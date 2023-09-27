package com.example.qcassistant.service.study;

import com.example.qcassistant.repository.app.IqviaAppRepository;
import com.example.qcassistant.repository.app.MedableAppRepository;
import com.example.qcassistant.repository.sponsor.IqviaSponsorRepository;
import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import com.example.qcassistant.repository.study.IqviaStudyRepository;
import com.example.qcassistant.repository.study.MedableStudyRepository;
import com.example.qcassistant.repository.study.environment.IqviaEnvironmentRepository;
import com.example.qcassistant.repository.study.environment.MedableEnvironmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IqviaStudyService extends BaseStudyService{

    private IqviaStudyRepository studyRepository;
    private IqviaAppRepository appRepository;
    private IqviaSponsorRepository sponsorRepository;
    private IqviaEnvironmentRepository environmentRepository;

    @Autowired
    public IqviaStudyService(ModelMapper modelMapper,
                             IqviaStudyRepository studyRepository,
                             IqviaAppRepository appRepository,
                             IqviaSponsorRepository sponsorRepository,
                             IqviaEnvironmentRepository environmentRepository) {
        super(modelMapper);
        this.studyRepository = studyRepository;
        this.appRepository = appRepository;
        this.sponsorRepository = sponsorRepository;
        this.environmentRepository = environmentRepository;
    }
}
