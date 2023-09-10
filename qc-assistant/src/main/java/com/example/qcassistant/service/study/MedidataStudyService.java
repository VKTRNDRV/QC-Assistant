package com.example.qcassistant.service.study;

import com.example.qcassistant.domain.dto.study.MedidataStudyAddDto;
import com.example.qcassistant.domain.dto.study.MedidataStudyDisplayDto;
import com.example.qcassistant.domain.dto.study.MedidataStudyEditDto;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import com.example.qcassistant.repository.sponsor.MedidataSponsorRepository;
import com.example.qcassistant.repository.study.MedidataStudyRepository;
import com.example.qcassistant.repository.study.environment.MedidataEnvironmentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedidataStudyService {

    private MedidataStudyRepository studyRepository;

    private MedidataAppRepository appRepository;

    private MedidataSponsorRepository sponsorRepository;

    private MedidataEnvironmentRepository environmentRepository;

    private ModelMapper modelMapper;

    @Autowired
    public MedidataStudyService(MedidataStudyRepository studyRepository, MedidataAppRepository appRepository, MedidataSponsorRepository sponsorRepository, MedidataEnvironmentRepository environmentRepository, ModelMapper modelMapper) {
        this.studyRepository = studyRepository;
        this.appRepository = appRepository;
        this.sponsorRepository = sponsorRepository;
        this.environmentRepository = environmentRepository;
        this.modelMapper = modelMapper;
    }

    public void addStudy(MedidataStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        MedidataStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    public void editStudy(MedidataStudyEditDto studyEditDto){
        validateStudyEdit(studyEditDto);
        MedidataStudy study = mapToEntity(studyEditDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private MedidataStudy mapToEntity(MedidataStudyEditDto studyEditDto) {
        MedidataStudy study = this.modelMapper.map(studyEditDto, MedidataStudy.class);
        List<MedidataApp> siteApps = getAppsByName(
                studyEditDto.getEnvironment().getSiteApps());
        List<MedidataApp> patientApps =
                getAppsByName(studyEditDto.getEnvironment().getPatientApps());
        MedidataSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyEditDto.getSponsor()).orElseThrow();
        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private MedidataStudy mapToEntity(MedidataStudyAddDto studyAddDto) {
        MedidataStudy study = this.modelMapper.map(studyAddDto, MedidataStudy.class);
        MedidataSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyAddDto.getSponsor()).orElseThrow();
        List<MedidataApp> siteApps = getAppsByName(
                studyAddDto.getEnvironment().getSiteApps());
        List<MedidataApp> patientApps = getAppsByName(
                studyAddDto.getEnvironment().getPatientApps());

        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private List<MedidataApp> getAppsByName(List<String> appNames) {
        List<MedidataApp> apps = new ArrayList<>();
        for(String name : appNames){
            Optional<MedidataApp> app = this.appRepository.findFirstByName(name);
            app.ifPresent(apps::add);
        }

        return apps;
    }

    private void validateStudyAdd(MedidataStudyAddDto studyAddDto) {
        validateNameNotBlank(studyAddDto.getName());
        validateUniqueName(studyAddDto.getName());
        validateAppsCount(studyAddDto.getEnvironment().getSiteApps(),
                studyAddDto.getEnvironment().getPatientApps());
    }

    private void validateStudyEdit(MedidataStudyEditDto studyEditDto) {
        validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    private void validateAppsCount(List<String> siteApps, List<String> patientApps){
        if(siteApps == null || siteApps.size() == 0){
            throw new RuntimeException("No Site apps selected");
        }
        if(patientApps == null || patientApps.size() == 0){
            throw new RuntimeException("No Patient apps selected");
        }
    }

    private void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    private void validateUniqueName(String name){
        if(this.studyRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Study \"" + name + "\" already present");
        }
    }

    public List<MedidataStudyDisplayDto> displayAllStudies() {
        return this.studyRepository.findAll().stream()
                .map(s -> new MedidataStudyDisplayDto()
                        .setId(s.getId())
                        .setName(s.getName())
                        .setSponsor(s.getSponsor().getName()))
                .sorted((s1,s2) -> {
                    int result = s1.getSponsor().compareTo(s2.getSponsor());
                    if(result == 0){
                        result = s1.getName().compareTo(s2.getName());
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    public MedidataStudyEditDto getStudyEditById(Long id) {
        MedidataStudy study = this.studyRepository
                .findById(id).orElseThrow();
        MedidataStudyEditDto editDto = this.modelMapper
                .map(study, MedidataStudyEditDto.class);
        editDto.setManualFields(study);
        return editDto;
    }
}
