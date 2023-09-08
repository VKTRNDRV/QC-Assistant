package com.example.qcassistant.service;

import com.example.qcassistant.domain.dto.DestinationAddDto;
import com.example.qcassistant.domain.dto.DestinationDisplayDto;
import com.example.qcassistant.domain.dto.DestinationEditDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.repository.DestinationRepository;
import com.example.qcassistant.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private DestinationRepository destinationRepository;

    private LanguageRepository languageRepository;

    private ModelMapper modelMapper;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository, LanguageRepository languageRepository, ModelMapper modelMapper) {
        this.destinationRepository = destinationRepository;
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    public void addDestination(DestinationAddDto destinationAddDto) {
        validateAddDestination(destinationAddDto);
        Destination destination = this.modelMapper.map(destinationAddDto, Destination.class);
        List<Language> languages = getLanguagesByName(destinationAddDto.getSelectedLanguages());
        destination.setLanguages(languages);
        this.destinationRepository.save(destination);
    }

    private List<Language> getLanguagesByName(List<String> langNames) {
        List<Language> languages = new ArrayList<>();
        for(String langName : langNames){
            Optional<Language> language = this
                    .languageRepository.findFirstByName(langName);
            language.ifPresent(languages::add);
        }
        return languages;
    }

    private void validateEditDestination(DestinationEditDto editDto) {
        if(editDto.getSelectedLanguages() == null ||
                editDto.getSelectedLanguages().size() == 0){
            throw new RuntimeException("No languages selected");
        }
        if(editDto.getName().trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    private void validateAddDestination(DestinationAddDto addDto) {
        if(addDto.getSelectedLanguages() == null ||
                addDto.getSelectedLanguages().size() == 0){
            throw new RuntimeException("No languages selected");
        }
        if(addDto.getName().trim().isEmpty()){
           throw new RuntimeException("Name cannot be blank");
        }
        if(this.destinationRepository.findFirstByName(
                addDto.getName()).isPresent()){
            throw new RuntimeException(
                    "Destination " + addDto.getName() + " already present");
        }
    }

    public List<DestinationDisplayDto> displayDestinations(){
        List<Destination> entities = this.destinationRepository.findAll();
        List<DestinationDisplayDto> dtos = new ArrayList<>();
        for(Destination entity : entities){
            DestinationDisplayDto dto = this.modelMapper
                    .map(entity, DestinationDisplayDto.class);
            dto.setLanguages(String.join(", ",
                    entity.getLanguages().stream()
                            .map(Language::getName)
                            .collect(Collectors.toList())));
            dtos.add(dto);
        }

        return dtos.stream()
                .sorted((d1, d2) -> d1.getName().compareTo(d2.getName()))
                .collect(Collectors.toList());
    }

    public DestinationEditDto getDestinationEditById(Long id){
        Destination destination = this.destinationRepository.findById(id).get();
        return this.modelMapper.map(destination, DestinationEditDto.class);
    }

    public void updateDestination(DestinationEditDto editDto) {
        validateEditDestination(editDto);
        Destination destination = this.modelMapper.map(editDto, Destination.class);
        destination.setLanguages(getLanguagesByName(editDto.getSelectedLanguages()));
        this.destinationRepository.save(destination);
    }
}
