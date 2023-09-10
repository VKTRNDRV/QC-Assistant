package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.domain.dto.sponsor.MedidataSponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.MedidataSponsorDisplayDto;
import com.example.qcassistant.domain.dto.sponsor.MedidataSponsorEditDto;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.repository.sponsor.MedidataSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedidataSponsorService {

    private MedidataSponsorRepository sponsorRepository;

    private ModelMapper modelMapper;

    @Autowired
    public MedidataSponsorService(MedidataSponsorRepository sponsorRepository, ModelMapper modelMapper) {
        this.sponsorRepository = sponsorRepository;
        this.modelMapper = modelMapper;
    }


    public List<MedidataSponsorDisplayDto> displayAllSponsors() {
        return this.sponsorRepository.findAll()
                .stream().map(s -> this.modelMapper
                        .map(s, MedidataSponsorDisplayDto.class))
                .sorted((s1,s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());
    }

    public void addSponsor(MedidataSponsorAddDto sponsorAddDto) {
        validateAddSponsor(sponsorAddDto);
        MedidataSponsor sponsor = this.modelMapper.map(
                sponsorAddDto, MedidataSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    private void validateAddSponsor(MedidataSponsorAddDto sponsorAddDto) {
        String name = sponsorAddDto.getName();
        validateNameNotBlank(name);
        validateUniqueName(name);
    }

    public MedidataSponsorEditDto getSponsorEditById(Long id) {
        return this.modelMapper.map(
                this.sponsorRepository.findById(id)
                        .orElseThrow(),
                MedidataSponsorEditDto.class);
    }

    public void editSponsor(MedidataSponsorEditDto sponsorEditDto) {
        validateEditSponsor(sponsorEditDto);
        MedidataSponsor sponsor = this.modelMapper.map(sponsorEditDto, MedidataSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    private void validateEditSponsor(MedidataSponsorEditDto sponsorEditDto) {
        String name = sponsorEditDto.getName();
        validateNameNotBlank(name);
        if(!this.sponsorRepository.findById(sponsorEditDto.getId()).get()
                .getName().trim()
                .equals(sponsorEditDto.getName())){
            validateUniqueName(name);
        }
    }

    private void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be null");
        }
    }

    private void validateUniqueName(String name){
        if(this.sponsorRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Sponsor \"" + name + "\" already present");
        }
    }
}
