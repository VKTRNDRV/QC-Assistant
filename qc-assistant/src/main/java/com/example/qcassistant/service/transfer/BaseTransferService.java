package com.example.qcassistant.service.transfer;

import com.example.qcassistant.service.DestinationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseTransferService {

    protected DestinationService destinationService;

    protected ModelMapper modelMapper;

    @Autowired
    public BaseTransferService(DestinationService destinationService, ModelMapper modelMapper) {
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }
}
