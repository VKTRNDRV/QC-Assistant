package com.example.qcassistant.service.sponsor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseSponsorService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseSponsorService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }
}
