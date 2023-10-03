package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.entity.app.BaseApp;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.study.BaseStudy;
import com.example.qcassistant.domain.entity.study.environment.BaseEnvironment;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.item.PlugType;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.item.sim.SerializedSIM;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.ClinicalOrder;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.domain.order.SimRepository;
import com.example.qcassistant.regex.OrderInputRegex;
import com.example.qcassistant.util.TrinaryBoolean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public abstract class NoteGenerationService {

    protected ModelMapper modelMapper;
    protected static final int LARGE_ORDER_COUNT = 10;

    @Autowired
    protected NoteGenerationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected Collection<ItemNameSerialDto> mapDevices(ClinicalOrder order) {
        Collection<ItemNameSerialDto> deviceDTOs = new ArrayList<>();
        order.getDeviceRepository().getDevices().forEach(d -> {
                deviceDTOs.add(this.mapDeviceToSerialDTO(d));
        });
        return deviceDTOs;
    }

    private ItemNameSerialDto mapDeviceToSerialDTO(Device device) {
        return this.modelMapper.map(device, ItemNameSerialDto.class);
    }

    protected <T extends ClinicalOrder> Collection<Note> genCommentNotes(T order) {
        Collection<Note> notes = new ArrayList<>();
        Pattern pattern = Pattern.compile(OrderInputRegex.SPECIAL_INSTRUCTIONS_REGEX);
        Matcher matcher = pattern.matcher(order.getOrderTermComments());
        if(matcher.find()){
            notes.add(new Note(Severity.MEDIUM, NoteText.SPECIAL_INSTRUCTIONS));
        }

        return notes;
    }

    protected String getAppNamesList(Collection<? extends BaseApp> apps){
        return apps.stream().map(BaseApp::getName)
                .collect(Collectors.joining(", "));
    }

    protected Collection<ItemNameSerialDto> mapSims(SimRepository simRepository) {
        Collection<ItemNameSerialDto> simDTOs = new ArrayList<>();
        simRepository.getSims().forEach(
                s -> simDTOs.add(this.mapSimToSerialDto(s)));

        return simDTOs;
    }

    private ItemNameSerialDto mapSimToSerialDto(SerializedSIM sim){
        return this.modelMapper.map(sim, ItemNameSerialDto.class);
    }

    protected <T extends ClinicalOrder> Collection<Note> getDestinationAccessoryNotes(T order){
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();

        if(order.getDeviceRepository().count() > LARGE_ORDER_COUNT &&
                !destination.getName().equals(Destination.ISRAEL)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_CHARGER_COUNT));
        }

        if(!destination.isUnknown()){
            if(!destination.getPlugType().equals(PlugType.C)){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.VERIFY_PLUG_TYPE, destination.getPlugType().name())));
            }
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UNKNOWN_PLUG_TYPE));
        }

        return notes;
    }

    protected <T extends ClinicalOrder> Collection<Note> genSpecialDeviceReqNotes(T order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_SERIALS));
        Destination destination = order.getDestination();

        switch (destination.getRequiresSpecialModels()){
            case TRUE: notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_DVC_MODELS));
                break;
            case UNKNOWN: notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_DESTINATION_FOR_SPECIAL_MODELS));
                break;
            default:
                break;
        }

        if(destination.getRequiresUnusedDevices()
                .equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UNUSED_DEVICES));
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_BOX_SERIALS));
        }

        if(destination.getName().equals(Destination.TURKEY)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_CE_MARKS));
        }

        return notes;
    }

    protected <T extends ClinicalOrder> Collection<Note> getBaseEnvironmentNotes(T order){
        Collection<Note> notes = new ArrayList<>();
        BaseEnvironment environment = order.getStudy().getEnvironment();
        TrinaryBoolean isSitePatientSeparated = environment.getIsSitePatientSeparated();
        TrinaryBoolean isDestinationSeparated = environment.getIsDestinationSeparated();
        if(!(isDestinationSeparated.equals(TrinaryBoolean.FALSE) &&
                isSitePatientSeparated.equals(TrinaryBoolean.FALSE))){

            notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_APPROPRIATE_GROUP));

            if(isSitePatientSeparated.equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.IS_SITE_PATIENT_SEPARATED));
            }

            if(isDestinationSeparated.equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.IS_DESTINATION_SEPARATED));
            }
        }

        if(order.containsSiteDevices() && this
                .anyRequiresCamera(environment.getSiteApps())){
            notes.add(new Note(Severity.LOW, NoteText.SITE_APPS_CAMERA));
        }

        if(order.containsPatientDevices() && this
                .anyRequiresCamera(environment.getPatientApps())){
            notes.add(new Note(Severity.LOW, NoteText.PATIENT_APPS_CAMERA));
        }

        return notes;
    }

    protected <T extends BaseApp> boolean anyRequiresCamera(Collection<T> apps){
        for(T app : apps){
            if(app.getRequiresCamera().equals(TrinaryBoolean.TRUE)){
                return true;
            }
        }

        return false;
    }
}
