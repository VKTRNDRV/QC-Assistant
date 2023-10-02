package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.PlugType;
import com.example.qcassistant.domain.item.accessory.MedableAccessory;
import com.example.qcassistant.domain.item.device.medical.MedableMedicalDevice;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.AccessoryRepository;
import com.example.qcassistant.domain.order.DeviceRepository;
import com.example.qcassistant.domain.order.MedableOrder;
import com.example.qcassistant.service.study.MedableStudyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedableNoteGenerationService extends NoteGenerationService{

    private MedableStudyService studyService;

    @Autowired
    public MedableNoteGenerationService(ModelMapper modelMapper, MedableStudyService studyService) {
        super(modelMapper);
        this.studyService = studyService;
    }

    public MedableOrderNotesDto generateNotes(MedableOrder order){
        MedableOrderNotesDto notes = new MedableOrderNotesDto();
        Collection<ItemNameSerialDto> serializedItems = new ArrayList<>();
        serializedItems.addAll(super.mapSims(order.getSimRepository()));
        serializedItems.addAll(super.mapDevices(order));
        notes.setItems(serializedItems)
                .sortItems();


        notes.setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));



        return notes;
    }

    private Collection<Note> genDocumentationNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_ASSET_LABEL));
        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_ASSET_NUM_ON_LABEL));
        if(order.containsSiteDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_MEDABLE_SITE_CARTONS));
        }
        if(order.getDeviceRepository().containsMedicalDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.BUILD_DOCS_BY_HAND));
        }

        notes.addAll(super.genCommentNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();
        if(destination.isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_DESTINATION));
        }

        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>(super
                .genSpecialDeviceReqNotes(order));
        Optional<List<String>> duplicates = order
                .getDeviceRepository().getDuplicateSerials();
        if(duplicates.isPresent()){
            notes.add(new Note(Severity.HIGH, String.format(
                    NoteText.DUPLICATE_SERIALS,
                    String.join(", ", duplicates.get()))));
        }
        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        DeviceRepository devices = order.getDeviceRepository();
        AccessoryRepository accessories = order.getAccessoryRepository();
        notes.addAll(super.getDestinationAccessoryNotes(order));

        if(devices.containsDvcsWithConnector(ConnectorType.MICRO_USB)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(accessories.containsAccessory(
                MedableAccessory.MOTOROLA_HEADPHONES.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }

        if(accessories.containsAccessory(
                MedableAccessory.SCREEN_PROTECTOR.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SCREEN_PROTECTOR_PRESENT));
        }

        if(devices.containsMedicalDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.MEDICAL_DVCS_TL_QC));

            if(devices.containsAnyOfTheFollowing(List.of(
                    MedableMedicalDevice.IWATCH_SERIES_5.getShortName(),
                    MedableMedicalDevice.WATCH_SERIES_6.getShortName(),
                    MedableMedicalDevice.WATCH_SE.getShortName(),
                    MedableMedicalDevice.WATCH_SE_2ND_GEN.getShortName()))){

                notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_IWATCHES_CHARGED));
            }
        }

        return notes;
    }
}
