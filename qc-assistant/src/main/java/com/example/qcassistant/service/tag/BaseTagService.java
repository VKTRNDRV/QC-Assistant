package com.example.qcassistant.service.tag;

import com.example.qcassistant.domain.dto.destination.DestinationAddDto;
import com.example.qcassistant.domain.dto.tag.TagAddDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.tag.BaseTag;
import com.example.qcassistant.repository.DestinationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class BaseTagService {

    protected ModelMapper modelMapper;

    protected DestinationRepository destinationRepository;

    @Autowired
    public BaseTagService(ModelMapper modelMapper, DestinationRepository destinationRepository){
        this.modelMapper = modelMapper;
        this.destinationRepository = destinationRepository;
    }

    public abstract <T extends BaseTag> List<T> getEntities();
    public abstract void addTag(TagAddDto tagAddDto);
    public abstract void editTag();
    public abstract <T extends BaseTag> T getTagById(Long id);

    protected void validateTagAdd(TagAddDto tagAddDto) {
        if(tagAddDto.getText().trim().isEmpty()){
            throw new RuntimeException("Note text cannot be blank");
        }
    }
    protected List<Destination> getDestinationsByNames(Collection<String> destinationNames){
        List<Destination> destinations = new ArrayList<>();
        for(String destinationName : destinationNames){
            Optional<Destination> opt = this.destinationRepository
                    .findFirstByName(destinationName);
            opt.ifPresent(destinations::add);
        }

        return destinations;
    }
}
