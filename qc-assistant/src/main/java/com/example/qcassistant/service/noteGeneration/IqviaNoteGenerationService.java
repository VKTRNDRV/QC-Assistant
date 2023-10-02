package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.item.accessory.Accessory;
import com.example.qcassistant.domain.item.accessory.IqviaAccessory;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.AccessoryRepository;
import com.example.qcassistant.domain.order.IqviaOrder;
import com.example.qcassistant.service.study.IqviaStudyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class IqviaNoteGenerationService extends NoteGenerationService{

    private IqviaStudyService studyService;

    @Autowired
    public IqviaNoteGenerationService(ModelMapper modelMapper, IqviaStudyService studyService) {
        super(modelMapper);
        this.studyService = studyService;
    }

    public IqviaOrderNotesDto generateNotes(IqviaOrder order){
        IqviaOrderNotesDto notes = new IqviaOrderNotesDto();
        Collection<ItemNameSerialDto> serializedItems = new ArrayList<>();
        serializedItems.addAll(super.mapDevices(order));
        serializedItems.addAll(super.mapSims(order.getSimRepository()));
        notes.setItems(serializedItems)
                .sortItems();

        notes.setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));


        return notes;
    }

    private Collection<Note> genDocumentationNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.addAll(super.genCommentNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getDestination().isUnknown()){
            Note unknownDestination = new Note(Severity.HIGH,
                    NoteText.ADD_UNKNOWN_DESTINATION);
            notes.add(unknownDestination);
        }

        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(super.genSpecialDeviceReqNotes(order));
        Optional<List<String>> duplicates = order
                .getDeviceRepository().getDuplicateSerials();
        if(duplicates.isPresent()){
            notes.add(new Note(Severity.HIGH, String.format(
                    NoteText.DUPLICATE_SERIALS,
                    String.join(", ", duplicates.get()))));
        }
        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        AccessoryRepository accessories = order.getAccessoryRepository();
        notes.addAll(super.getDestinationAccessoryNotes(order));
        if(order.getDeviceRepository()
                .containsDvcsWithConnector(ConnectorType.MICRO_USB)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.IPAD_STYLUS.getShortName(),
                IqviaAccessory.TABLET_STYLUS.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STYLUS_PRESENT));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.APPLE_HEADPHONES.getShortName(),
                IqviaAccessory.SAMSUNG_HEADPHONES.getShortName(),
                IqviaAccessory.MOTOROLA_HEADPHONES.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.TABLET_STAND.getShortName(),
                IqviaAccessory.PHONE_STAND.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STANDS_PRESENT));
        }

        return notes;
    }
}
