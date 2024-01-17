package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.repository.sponsor.MedableSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedableSponsorService extends BaseSponsorService{

    private MedableSponsorRepository sponsorRepository;

    @Autowired
    public MedableSponsorService(ModelMapper modelMapper, MedableSponsorRepository sponsorRepository) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public void addSponsor(SponsorAddDto sponsorAddDto) {
        validateAddSponsor(sponsorAddDto);
        MedableSponsor sponsor = this.modelMapper.map(
                sponsorAddDto, MedableSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    public void editSponsor(SponsorEditDto sponsorEditDto) {
        validateEditSponsor(sponsorEditDto);
        MedableSponsor sponsor = this.modelMapper.map(sponsorEditDto, MedableSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    public Optional<MedableSponsor> getSponsorByName(String name) {
        return this.sponsorRepository.findFirstByName(name);
    }

    @Override
    public <T extends BaseSponsor> void saveAll(Collection<T> sponsors) {
        this.sponsorRepository.saveAll((Collection<MedableSponsor>) sponsors);
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
