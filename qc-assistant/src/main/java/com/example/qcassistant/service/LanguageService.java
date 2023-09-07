package com.example.qcassistant.service;

import com.example.qcassistant.domain.dto.LanguageDto;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.exception.ExistingUsernameException;
import com.example.qcassistant.repository.LanguageRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private LanguageRepository languageRepository;

    private ModelMapper modelMapper;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, ModelMapper modelMapper) {
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init(){
        if(this.languageRepository.count() == 0){
            Language eng = new Language();
            eng.setName("English");
            this.languageRepository.save(eng);
        }
    }

    public List<LanguageDto> getAllLanguages(){
        List<LanguageDto> languages = this.languageRepository
                .findAll().stream()
                .map(l -> this.modelMapper.map(l, LanguageDto.class))
                .collect(Collectors.toList());
        languages.sort((l1, l2) -> l1.getName().compareTo(l2.getName()));
        return languages;
    }


    public LanguageDto getLanguageById(Long id){
        return this.modelMapper.map(
                this.languageRepository.findById(id).orElseThrow(),
                LanguageDto.class);
    }

    public void editLanguage(LanguageDto languageDto){
        validateLanguageDto(languageDto);
        Language language = this.modelMapper.map(languageDto, Language.class);
        this.languageRepository.save(language);
    }

    public void addLanguage(LanguageDto languageDto){
        validateLanguageDto(languageDto);
        Language language = this.modelMapper.map(languageDto, Language.class);
        this.languageRepository.save(language);
    }

    private void validateLanguageDto(LanguageDto languageDto) {
        if(languageDto.getName().trim().isEmpty()){
            throw new RuntimeException("Language Name cannot be blank");
        }
        if(this.languageRepository.findFirstByName(
                languageDto.getName()).isPresent()){
            throw new RuntimeException(
                    "Language " + languageDto.getName() + " already present");
        }
    }
}
