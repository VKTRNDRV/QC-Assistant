package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.enums.Severity;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.note.Note;
import com.example.qcassistant.domain.note.noteText.NoteText;
import com.example.qcassistant.domain.order.ClinicalOrder;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.regex.OrderInputRegex;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public abstract class NoteGenerationService {

    private ModelMapper modelMapper;

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

    protected Collection<Note> genCommentNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Pattern pattern = Pattern.compile(OrderInputRegex.SPECIAL_INSTRUCTIONS_REGEX);
        Matcher matcher = pattern.matcher(order.getOrderTermComments());
        if(matcher.find()){
            notes.add(new Note(Severity.MEDIUM, NoteText.SPECIAL_INSTRUCTIONS));
        }

        return notes;
    }
}
