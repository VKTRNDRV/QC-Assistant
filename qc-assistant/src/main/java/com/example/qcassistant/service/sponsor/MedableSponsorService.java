package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.domain.dto.sponsor.sponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.sponsorDisplayDto;
import com.example.qcassistant.domain.dto.sponsor.sponsorEditDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.repository.sponsor.IqviaSponsorRepository;
import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableSponsorService extends BaseSponsorService{

    private MedableSponsorRepository sponsorRepository;

    @Autowired
    public MedableSponsorService(ModelMapper modelMapper, MedableSponsorRepository sponsorRepository) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public void addSponsor(sponsorAddDto sponsorAddDto) {
        validateAddSponsor(sponsorAddDto);
        MedableSponsor sponsor = this.modelMapper.map(
                sponsorAddDto, MedableSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    public void editSponsor(sponsorEditDto sponsorEditDto) {
        validateEditSponsor(sponsorEditDto);
        MedableSponsor sponsor = this.modelMapper.map(sponsorEditDto, MedableSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    protected Optional<MedableSponsor> findFirstByName(String name) {
        return getSponsorRepository().findFirstByName(name);
    }

    @Override
    protected MedableSponsorRepository getSponsorRepository() {
        return this.sponsorRepository;
    }

    @Override
    public List<MedableSponsor> getEntities() {
        return this.sponsorRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }
}
