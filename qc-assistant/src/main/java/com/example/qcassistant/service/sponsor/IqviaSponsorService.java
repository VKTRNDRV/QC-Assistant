package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.repository.sponsor.IqviaSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IqviaSponsorService extends BaseSponsorService{

    private IqviaSponsorRepository sponsorRepository;

    @Autowired
    public IqviaSponsorService(ModelMapper modelMapper, IqviaSponsorRepository sponsorRepository) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }
}
