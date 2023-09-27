package com.example.qcassistant.service.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseAppService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseAppService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be null");
        }
    }

    protected abstract void validateUniqueName(String name);
}
