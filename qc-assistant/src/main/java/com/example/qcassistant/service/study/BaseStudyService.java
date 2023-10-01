package com.example.qcassistant.service.study;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    protected void validateAppsCount(List<String> siteApps, List<String> patientApps){
        if(siteApps == null || siteApps.size() == 0){
            throw new RuntimeException("No Site apps selected");
        }
        if(patientApps == null || patientApps.size() == 0){
            throw new RuntimeException("No Patient apps selected");
        }
    }

}
