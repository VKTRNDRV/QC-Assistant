package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.repository.sponsor.MedidataSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedidataSponsorService extends BaseSponsorService{

    private MedidataSponsorRepository sponsorRepository;


    @Autowired
    public MedidataSponsorService(MedidataSponsorRepository sponsorRepository, ModelMapper modelMapper) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public List<MedidataSponsor> getEntities(){
        return this.sponsorRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }

    @Override
    public Optional<MedidataSponsor> findFirstByName(String name) {
        return getSponsorRepository().findFirstByName(name);
    }

    @Override
    protected MedidataSponsorRepository getSponsorRepository() {
        return this.sponsorRepository;
    }

    @Override
    public void addSponsor(SponsorAddDto sponsorAddDto) {
        validateAddSponsor(sponsorAddDto);
        MedidataSponsor sponsor = this.modelMapper.map(
                sponsorAddDto, MedidataSponsor.class);
        getSponsorRepository().save(sponsor);
    }

    @Override
    public void editSponsor(SponsorEditDto sponsorEditDto) {
        validateEditSponsor(sponsorEditDto);
        MedidataSponsor sponsor = this.modelMapper.map(sponsorEditDto, MedidataSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    public MedidataSponsor getUnknownSponsor() {
        return getSponsorRepository().findFirstByName(BaseEntity.UNKNOWN).get();
    }
}
