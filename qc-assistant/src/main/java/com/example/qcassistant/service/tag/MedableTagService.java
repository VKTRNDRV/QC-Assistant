package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.domain.dto.tag.TagDisplayDto;
import com.example.qcassistant.domain.entity.study.MedableStudy;
import com.example.qcassistant.domain.entity.tag.MedableTag;
import com.example.qcassistant.repository.DestinationRepository;
import com.example.qcassistant.repository.study.MedableStudyRepository;
import com.example.qcassistant.repository.tag.MedableTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedableTagService extends BaseTagService{

    private MedableTagRepository tagRepository;

    private MedableStudyRepository studyRepository;

    @Autowired
    public MedableTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, MedableTagRepository tagService, MedableStudyRepository studyRepository) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagService;
        this.studyRepository = studyRepository;
    }

    @Override
    public List<MedableTag> getEntities() {
        return null;
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {
        super.validateTagAdd(tagAddDto);
        MedableTag tag = this.mapToEntity(tagAddDto);
        this.tagRepository.save(tag);
    }

    private MedableTag mapToEntity(TagAddDto tagAddDto) {
        MedableTag tag = super.modelMapper
                .map(tagAddDto, MedableTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private List<MedableStudy> getStudiesByNames(List<String> studyNames) {
        List<MedableStudy> studies = new ArrayList<>();
        for(String studyName : studyNames){
            Optional<MedableStudy> study = this.studyRepository
                    .findFirstByName(studyName);
            study.ifPresent(studies::add);
        }

        return studies;
    }

    @Override
    public List<TagDisplayDto> getDisplayTags(){
        return super.mapToDisplayDto(this.tagRepository.findAll());
    }

    @Override
    public void editTag() {

    }

    @Override
    public MedableTag getTagById(Long id) {
        return null;
    }
}
