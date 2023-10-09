package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.entity.tag.BaseTag;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.repository.tag.MedidataTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedidataTagService extends BaseTagService{

    private MedidataTagRepository tagRepository;

    @Autowired
    public MedidataTagService(ModelMapper modelMapper, MedidataTagRepository tagRepository) {
        super(modelMapper);
        this.tagRepository = tagRepository;
    }

    @Override
    public List<MedidataTag> getEntities() {
        return null;
    }

    @Override
    public void addTag() {

    }

    @Override
    public void editTag() {

    }

    @Override
    public MedidataTag getTagById(Long id) {
        return null;
    }
}
