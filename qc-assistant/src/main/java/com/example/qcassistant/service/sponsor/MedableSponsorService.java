package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedableSponsorService extends BaseSponsorService{

    private MedableSponsorRepository sponsorRepository;

    @Autowired
    public MedableSponsorService(ModelMapper modelMapper, MedableSponsorRepository sponsorRepository) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }
}
