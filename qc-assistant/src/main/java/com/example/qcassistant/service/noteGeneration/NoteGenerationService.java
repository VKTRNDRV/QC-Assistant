package com.example.qcassistant.service.noteGeneration;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.order.MedidataOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public abstract class NoteGenerationService {

    private ModelMapper modelMapper;

    @Autowired
    protected NoteGenerationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected Collection<ItemNameSerialDto> mapDevices(MedidataOrder order) {
        Collection<ItemNameSerialDto> deviceDTOs = new ArrayList<>();
        order.getDeviceRepository().getDevices().forEach(d -> {
                deviceDTOs.add(this.mapDeviceToSerialDTO(d));
        });
        return deviceDTOs;
    }

    private ItemNameSerialDto mapDeviceToSerialDTO(Device device) {
        return this.modelMapper.map(device, ItemNameSerialDto.class);
    }
}
