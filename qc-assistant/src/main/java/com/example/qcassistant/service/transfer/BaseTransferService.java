package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.app.AppTransferDTO;
import com.example.qcassistant.domain.dto.sponsor.SponsorTransferDTO;
import com.example.qcassistant.domain.dto.tag.TagTransferDTO;
import com.example.qcassistant.domain.entity.app.BaseApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.study.BaseStudy;
import com.example.qcassistant.domain.entity.tag.BaseTag;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.service.DestinationService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class BaseTransferService {

    protected DestinationService destinationService;

    protected ModelMapper modelMapper;

    protected Gson gson;

    @Autowired
    public BaseTransferService(DestinationService destinationService, ModelMapper modelMapper) {
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
    }

    protected <T extends BaseSponsor> List<SponsorTransferDTO> mapSponsorsToTransferDTO(Collection<T> sponsors) {
        List<SponsorTransferDTO> transferDTOs = new ArrayList<>();
        for(T sponsor : sponsors){
            transferDTOs.add(this.modelMapper
                    .map(sponsor, SponsorTransferDTO.class));
        }

        return transferDTOs;
    }

    protected <T extends BaseApp> List<AppTransferDTO> mapAppsToTransferDTO(Collection<T> apps) {
        List<AppTransferDTO> transferDTOs = new ArrayList<>();
        for(T app : apps){
            transferDTOs.add(this.modelMapper
                    .map(app, AppTransferDTO.class));
        }

        return transferDTOs;
    }

    protected <T extends BaseTag> List<TagTransferDTO> mapTagsToTransferDTO(Collection<T> tags) {
        List<TagTransferDTO> transferDTOs = new ArrayList<>();
        for(T tag : tags){
            TagTransferDTO dto = this.modelMapper
                    .map(tag, TagTransferDTO.class);
            dto.setStudies(tag.getStudies().stream()
                    .map(BaseStudy::getName)
                    .collect(Collectors.toList()));
            dto.setDestinations(tag.getDestinations().stream()
                    .map(Destination::getName)
                    .collect(Collectors.toList()));

            transferDTOs.add(dto);
        }

        return transferDTOs;
    }
}
