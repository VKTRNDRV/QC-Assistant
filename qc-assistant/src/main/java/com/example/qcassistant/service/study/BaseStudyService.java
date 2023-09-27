package com.example.qcassistant.service.study;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseStudyService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseStudyService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }
}
