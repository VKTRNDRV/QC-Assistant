package com.example.qcassistant.service.study;

import com.example.qcassistant.repository.app.MedableAppRepository;
import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import com.example.qcassistant.repository.study.MedableStudyRepository;
import com.example.qcassistant.repository.study.environment.MedableEnvironmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
