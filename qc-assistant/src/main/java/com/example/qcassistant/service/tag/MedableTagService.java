package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.domain.entity.tag.MedableTag;
import com.example.qcassistant.repository.DestinationRepository;
import com.example.qcassistant.repository.tag.MedableTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedableTagService extends BaseTagService{

    private MedableTagRepository tagRepository;

    @Autowired
    public MedableTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, MedableTagRepository tagService) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagService;
    }

    @Override
    public List<MedableTag> getEntities() {
        return null;
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {

    }

    @Override
    public void editTag() {

    }

    @Override
    public MedableTag getTagById(Long id) {
        return null;
    }
}
