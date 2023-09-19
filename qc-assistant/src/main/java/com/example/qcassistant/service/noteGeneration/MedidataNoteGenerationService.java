package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.OrderNotesDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.item.PlugType;
import com.example.qcassistant.domain.item.accessory.MedidataAccessory;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.util.TrinaryBoolean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MedidataNoteGenerationService extends NoteGenerationService {

    private static final int LARGE_ORDER_COUNT = 10;
    private static final String ISRAEL = "Israel";
    private static final String TURKEY = "Turkey";

    @Autowired
    public MedidataNoteGenerationService(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public OrderNotesDto generateNotes(MedidataOrder order) {
        OrderNotesDto notes = new OrderNotesDto();
        notes.setItems(super.mapDevices(order));
        Collection<Note> shellCheckNotes = this.generateShellCheckNotes(order);
        return notes;
    }

    private Collection<Note> generateShellCheckNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));
        return notes;
    }

    private Collection<? extends Note> genShellCheckDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genSpecialDeviceReqNotes(order));
        return notes;
    }

    private Collection<? extends Note> genSpecialDeviceReqNotes(MedidataOrder order) {
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

        if(destination.getName().equals(TURKEY)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_CE_MARKS));
        }

        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();

        if(order.getDeviceRepository().count() > LARGE_ORDER_COUNT &&
                !destination.getName().equals(ISRAEL)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_CHARGER_COUNT));
        }

        if(!destination.getPlugType().equals(PlugType.C)){
            notes.add(new Note(Severity.MEDIUM, String.format(
                    NoteText.VERIFY_PLUG_TYPE, destination.getPlugType().name())));
        }

        if(!destination.isUnknown()){
            if(!destination.getSimType()
                    .equals(order.getSimType())){
                notes.add(new Note(Severity.HIGH, NoteText.SIM_TYPE_NOT_MATCHING));
            }
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SIM_TYPE));
        }


        if(order.getDeviceRepository()
                .containsAndroidDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(order.getStudy().getIncludesHeadphonesStyluses()
                .equals(TrinaryBoolean.TRUE) &&
                order.containsSiteDevices()){
            notes.addAll(this.generateStylusHeadphonesNotes(order));
        }

        return notes;
    }

    private Collection<Note> generateStylusHeadphonesNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        if(order.getAccessoryRepository().containsAccessory(
                MedidataAccessory.HEADPHONES.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }else{
            notes.add(new Note(Severity.HIGH, NoteText.HEADPHONES_NOT_DETECTED));
        }

        if(order.getAccessoryRepository().containsAccessory(
                MedidataAccessory.STYLUS.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STYLUS_PRESENT));
        }else{
            notes.add(new Note(Severity.HIGH, NoteText.STYLUSES_NOT_DETECTED));
        }

        return notes;
    }
}
