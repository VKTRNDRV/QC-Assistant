package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.entity.tag.BaseTag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class BaseTagService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseTagService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public abstract <T extends BaseTag> List<T> getEntities();
    public abstract void addTag();
    public abstract void editTag();
    public abstract <T extends BaseTag> T getTagById(Long id);
}
