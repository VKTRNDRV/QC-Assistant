package com.example.qcassistant.service.study;

import com.example.qcassistant.domain.dto.study.add.MedidataStudyAddDto;
import com.example.qcassistant.domain.dto.study.StudyDisplayDto;
import com.example.qcassistant.domain.dto.study.edit.MedidataStudyEditDto;
import com.example.qcassistant.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistant.repository.app.MedidataAppRepository;
import com.example.qcassistant.repository.sponsor.MedidataSponsorRepository;
import com.example.qcassistant.repository.study.MedidataStudyRepository;
import com.example.qcassistant.repository.study.environment.MedidataEnvironmentRepository;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedidataStudyService extends BaseStudyService{

    private MedidataStudyRepository studyRepository;
    private MedidataAppRepository appRepository;
    private MedidataSponsorRepository sponsorRepository;
    private MedidataEnvironmentRepository environmentRepository;


    @Autowired
    public MedidataStudyService(MedidataStudyRepository studyRepository,
                                MedidataAppRepository appRepository,
                                MedidataSponsorRepository sponsorRepository,
                                MedidataEnvironmentRepository environmentRepository,
                                ModelMapper modelMapper) {
        super(modelMapper);
        this.studyRepository = studyRepository;
        this.appRepository = appRepository;
        this.sponsorRepository = sponsorRepository;
        this.environmentRepository = environmentRepository;
    }

    @PostConstruct
    public void initUnknown(){
        if(this.studyRepository.findFirstByName(
                BaseEntity.UNKNOWN).isEmpty()){
            MedidataStudy study = new MedidataStudy();
            study.setName(BaseEntity.UNKNOWN)
                    .setFolderURL(BaseEntity.UNKNOWN);
            study.setContainsTranslatedDocs(TrinaryBoolean.UNKNOWN)
                    .setContainsTranslatedLabels(TrinaryBoolean.UNKNOWN)
                    .setContainsEditableWelcomeLetter(TrinaryBoolean.UNKNOWN)
                    .setIncludesHeadphonesStyluses(TrinaryBoolean.UNKNOWN)
                    .setIsPatientDeviceIpad(TrinaryBoolean.UNKNOWN);


            Optional<MedidataSponsor> optSponsor = this.sponsorRepository
                    .findFirstByName(BaseEntity.UNKNOWN);
            if(optSponsor.isPresent()){
                study.setSponsor(optSponsor.get());
            }else {
                MedidataSponsor sponsor = new MedidataSponsor();
                sponsor.setName(BaseEntity.UNKNOWN)
                        .setAreStudyNamesSimilar(TrinaryBoolean.UNKNOWN);
                this.sponsorRepository.save(sponsor);

                study.setSponsor(sponsor);
            }

            MedidataEnvironment environment = new MedidataEnvironment();
            environment.setPatientApps(new ArrayList<>())
                    .setSiteApps(new ArrayList<>())
                    .setIsLegacy(TrinaryBoolean.UNKNOWN)
                    .setIsSitePatientSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsDestinationSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsOsSeparated(TrinaryBoolean.UNKNOWN);
            this.environmentRepository.save(environment);

            study.setEnvironment(environment);

            this.studyRepository.save(study);
        }
    }

    public void addStudy(MedidataStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        studyAddDto.trimStringFields();
        MedidataStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    public void editStudy(MedidataStudyEditDto studyEditDto){
        validateStudyEdit(studyEditDto);
        studyEditDto.trimStringFields();
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
        studyAddDto.trimStringFields();
        super.validateNameNotBlank(studyAddDto.getName());
        validateUniqueName(studyAddDto.getName());
        validateAppsCount(studyAddDto.getEnvironment().getSiteApps(),
                studyAddDto.getEnvironment().getPatientApps());
    }

    private void validateStudyEdit(MedidataStudyEditDto studyEditDto) {
        studyEditDto.trimStringFields();
        super.validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    private void validateUniqueName(String name){
        if(this.studyRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Study \"" + name + "\" already present");
        }
    }

    public List<StudyDisplayDto> displayAllStudies() {
        return this.getEntities().stream()
                .map(s -> new StudyDisplayDto()
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

    public List<MedidataStudy> getEntities() {
        return this.studyRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }

    public MedidataStudy getUnknownStudy() {
        return this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();
    }

    public MedidataStudyInfoDto getStudyInfoById(Long id) {
        MedidataStudy study = this.studyRepository
                .findById(id).orElseThrow();

        MedidataStudyInfoDto dto = this.modelMapper
                .map(study, MedidataStudyInfoDto.class);
        dto.setSpecialFields(study);

        return dto;
    }
}
