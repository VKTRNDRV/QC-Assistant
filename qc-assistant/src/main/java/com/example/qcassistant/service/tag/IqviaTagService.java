package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.domain.entity.tag.BaseTag;
import com.example.qcassistant.domain.entity.tag.IqviaTag;
import com.example.qcassistant.repository.DestinationRepository;
import com.example.qcassistant.repository.tag.IqviaTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqviaTagService extends BaseTagService{

    private IqviaTagRepository tagRepository;

    @Autowired
    public IqviaTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, IqviaTagRepository tagRepository) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagRepository;
    }

    @Override
    public List<IqviaTag> getEntities() {
        return null;
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {

    }

    @Override
    public void editTag() {

    }

    @Override
    public IqviaTag getTagById(Long id) {
        return null;
    }
}
