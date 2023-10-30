package com.example.qcassistant.service;

import com.example.qcassistant.domain.dto.LanguageDto;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.regex.OrderInputRegex;
import com.example.qcassistant.repository.LanguageRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//        if(this.languageRepository.count() == 0){
//            Language eng = new Language();
//            eng.setName("English");
//            this.languageRepository.save(eng);
//        }

        if(this.languageRepository.count() > 0){
            return;
        }

        String[] langNames = OrderInputRegex.LANGUAGES_CSV
                .split(", ");
        List<Language> languages = new ArrayList<>();
        for(String languageName : langNames){
            languages.add(new Language()
                    .setName(languageName));
        }

        this.languageRepository.saveAll(languages);
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
        validateEditLanguage(languageDto);
        Language language = this.modelMapper.map(languageDto, Language.class);
        this.languageRepository.save(language);
    }

    private void validateEditLanguage(LanguageDto languageDto) {
        validateNameNotBlank(languageDto.getName());
        if(!this.languageRepository.findById(languageDto.getId()).get().getName()
                .equals(languageDto.getName())){
            validateUniqueName(languageDto.getName());
        }
    }

    public void addLanguage(LanguageDto languageDto){
        validateAddLanguage(languageDto);
        Language language = this.modelMapper.map(languageDto, Language.class);
        this.languageRepository.save(language);
    }

    private void validateAddLanguage(LanguageDto languageDto) {
        validateNameNotBlank(languageDto.getName());
        validateUniqueName(languageDto.getName());
    }

    private void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be null");
        }
    }

    private void validateUniqueName(String name){
        if(this.languageRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Language \"" + name + "\" already present");
        }
    }

    public List<Language> getEntities() {
        return this.languageRepository.findAll();
    }
}
