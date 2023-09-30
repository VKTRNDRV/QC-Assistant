package com.example.qcassistant.service.study;

import com.example.qcassistant.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistant.domain.dto.study.add.MedableStudyAddDto;
import com.example.qcassistant.domain.dto.study.edit.IqviaStudyEditDto;
import com.example.qcassistant.domain.dto.study.edit.MedableStudyEditDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.app.IqviaApp;
import com.example.qcassistant.domain.entity.app.MedableApp;
import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.study.IqviaStudy;
import com.example.qcassistant.domain.entity.study.MedableStudy;
import com.example.qcassistant.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistant.domain.entity.study.environment.MedableEnvironment;
import com.example.qcassistant.repository.app.MedableAppRepository;
import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import com.example.qcassistant.repository.study.MedableStudyRepository;
import com.example.qcassistant.repository.study.environment.MedableEnvironmentRepository;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedableStudyService extends BaseStudyService{

    private MedableStudyRepository studyRepository;
    private MedableAppRepository appRepository;
    private MedableSponsorRepository sponsorRepository;
    private MedableEnvironmentRepository environmentRepository;

    @Autowired
    public MedableStudyService(ModelMapper modelMapper,
                               MedableStudyRepository studyRepository,
                               MedableAppRepository appRepository,
                               MedableSponsorRepository sponsorRepository,
                               MedableEnvironmentRepository environmentRepository) {
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
            MedableStudy study = new MedableStudy();
            study.setName(BaseEntity.UNKNOWN)
                    .setFolderURL(BaseEntity.UNKNOWN);


            Optional<MedableSponsor> optSponsor = this.sponsorRepository
                    .findFirstByName(BaseEntity.UNKNOWN);
            if(optSponsor.isPresent()){
                study.setSponsor(optSponsor.get());
            }else {
                MedableSponsor sponsor = new MedableSponsor();
                sponsor.setName(BaseEntity.UNKNOWN)
                        .setAreStudyNamesSimilar(TrinaryBoolean.UNKNOWN);
                this.sponsorRepository.save(sponsor);

                study.setSponsor(sponsor);
            }

            MedableEnvironment environment = new MedableEnvironment();
            environment.setPatientApps(new ArrayList<>())
                    .setSiteApps(new ArrayList<>())
                    .setContainsChinaGroup(TrinaryBoolean.UNKNOWN)
                    .setIsSitePatientSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsDestinationSeparated(TrinaryBoolean.UNKNOWN);
            this.environmentRepository.save(environment);

            study.setEnvironment(environment);

            this.studyRepository.save(study);
        }
    }

    public void addStudy(MedableStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        studyAddDto.trimStringFields();
        MedableStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private void validateStudyAdd(MedableStudyAddDto studyAddDto) {
        studyAddDto.trimStringFields();
        super.validateNameNotBlank(studyAddDto.getName());
        validateUniqueName(studyAddDto.getName());
        validateAppsCount(studyAddDto.getEnvironment().getSiteApps(),
                studyAddDto.getEnvironment().getPatientApps());
    }

    private void validateUniqueName(String name){
        if(this.studyRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Study \"" + name + "\" already present");
        }
    }

    private MedableStudy mapToEntity(MedableStudyAddDto studyAddDto) {
        MedableStudy study = this.modelMapper.map(studyAddDto, MedableStudy.class);
        MedableSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyAddDto.getSponsor()).orElseThrow();
        List<MedableApp> siteApps = getAppsByName(
                studyAddDto.getEnvironment().getSiteApps());
        List<MedableApp> patientApps = getAppsByName(
                studyAddDto.getEnvironment().getPatientApps());

        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private List<MedableApp> getAppsByName(List<String> appNames) {
        List<MedableApp> apps = new ArrayList<>();
        for(String name : appNames){
            Optional<MedableApp> app = this.appRepository.findFirstByName(name);
            app.ifPresent(apps::add);
        }

        return apps;
    }

    public MedableStudyEditDto getStudyEditById(Long id) {
        MedableStudy study = this.studyRepository
                .findById(id).orElseThrow();
        MedableStudyEditDto editDto = this.modelMapper
                .map(study, MedableStudyEditDto.class);
        editDto.setManualFields(study);
        return editDto;
    }

    public void editStudy(MedableStudyEditDto studyEditDto) {
        validateStudyEdit(studyEditDto);
        studyEditDto.trimStringFields();
        MedableStudy study = mapToEntity(studyEditDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private void validateStudyEdit(MedableStudyEditDto studyEditDto) {
        studyEditDto.trimStringFields();
        super.validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    private MedableStudy mapToEntity(MedableStudyEditDto studyEditDto) {
        MedableStudy study = this.modelMapper.map(studyEditDto, MedableStudy.class);
        List<MedableApp> siteApps = getAppsByName(
                studyEditDto.getEnvironment().getSiteApps());
        List<MedableApp> patientApps = getAppsByName(
                studyEditDto.getEnvironment().getPatientApps());
        MedableSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyEditDto.getSponsor()).orElseThrow();
        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }
}
