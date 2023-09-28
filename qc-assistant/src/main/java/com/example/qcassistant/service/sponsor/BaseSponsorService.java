package com.example.qcassistant.service.sponsor;

import com.example.qcassistant.domain.dto.sponsor.sponsorAddDto;
import com.example.qcassistant.domain.dto.sponsor.sponsorDisplayDto;
import com.example.qcassistant.domain.dto.sponsor.sponsorEditDto;
import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class BaseSponsorService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseSponsorService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    public abstract <T extends BaseSponsor> List<T> getEntities();

    public sponsorEditDto getSponsorEditById(Long id) {
        return this.modelMapper.map(
                getSponsorRepository().findById(id)
                        .orElseThrow(),
                sponsorEditDto.class);
    }

    public List<sponsorDisplayDto> displayAllSponsors() {
        return this.getEntities().stream()
                .map(s -> this.modelMapper
                        .map(s, sponsorDisplayDto.class))
                .sorted((s1,s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());
    }

    protected void validateAddSponsor(sponsorAddDto sponsorAddDto) {
        sponsorAddDto.trimStringFields();
        String name = sponsorAddDto.getName();
        validateNameNotBlank(name);
        validateUniqueName(name);
    }

    protected void validateEditSponsor(sponsorEditDto sponsorEditDto) {
        sponsorEditDto.trimStringFields();
        String name = sponsorEditDto.getName();
        this.validateNameNotBlank(name);

        // if name changed - validate unique
        if(!getSponsorRepository().findById(sponsorEditDto.getId()).get()
                .getName().trim()
                .equals(sponsorEditDto.getName())){
            validateUniqueName(name);
        }
    }

    protected void validateUniqueName(String name){
        if(findFirstByName(name).isPresent()){
            throw new RuntimeException("Sponsor \"" + name + "\" already present");
        }
    }

    protected abstract  <T extends BaseSponsor> Optional<T> findFirstByName(String name);

    protected abstract <T extends BaseSponsor> CrudRepository<T, Long> getSponsorRepository();

    public abstract void addSponsor(sponsorAddDto sponsorAddDto);

    public abstract void editSponsor(sponsorEditDto sponsorEditDto);
}
