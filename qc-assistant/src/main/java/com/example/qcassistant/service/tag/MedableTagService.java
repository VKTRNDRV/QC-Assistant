package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.entity.tag.MedableTag;
import com.example.qcassistant.repository.tag.MedableTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedableTagService extends BaseTagService{

    private MedableTagRepository tagRepository;

    @Autowired
    public MedableTagService(ModelMapper modelMapper, MedableTagRepository tagService) {
        super(modelMapper);
        this.tagRepository = tagService;
    }

    @Override
    public List<MedableTag> getEntities() {
        return null;
    }

    @Override
    public void addTag() {

    }

    @Override
    public void editTag() {

    }

    @Override
    public MedableTag getTagById(Long id) {
        return null;
    }
}
