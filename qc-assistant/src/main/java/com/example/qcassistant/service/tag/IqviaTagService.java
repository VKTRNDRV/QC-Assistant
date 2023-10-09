package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.entity.tag.BaseTag;
import com.example.qcassistant.domain.entity.tag.IqviaTag;
import com.example.qcassistant.repository.tag.IqviaTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IqviaTagService extends BaseTagService{

    private IqviaTagRepository tagRepository;

    @Autowired
    public IqviaTagService(ModelMapper modelMapper, IqviaTagRepository tagRepository) {
        super(modelMapper);
        this.tagRepository = tagRepository;
    }

    @Override
    public List<IqviaTag> getEntities() {
        return null;
    }

    @Override
    public void addTag() {

    }

    @Override
    public void editTag() {

    }

    @Override
    public IqviaTag getTagById(Long id) {
        return null;
    }
}
