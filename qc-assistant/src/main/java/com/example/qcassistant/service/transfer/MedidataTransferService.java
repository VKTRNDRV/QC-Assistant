package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.study.transfer.MedidataStudyTransferDTO;
import com.example.qcassistant.domain.dto.transfer.MedidataEntitiesExportDTO;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.MedidataAppService;
import com.example.qcassistant.service.sponsor.MedidataSponsorService;
import com.example.qcassistant.service.study.MedidataStudyService;
import com.example.qcassistant.service.tag.MedidataTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MedidataTransferService extends BaseTransferService {

    private MedidataSponsorService sponsorService;

    private MedidataAppService appService;

    private MedidataTagService tagService;

    private MedidataStudyService studyService;

    @Autowired
    public MedidataTransferService(DestinationService destinationService,
                                   MedidataSponsorService sponsorService,
                                   MedidataAppService appService,
                                   MedidataTagService tagService,
                                   MedidataStudyService studyService,
                                   ModelMapper modelMapper) {
        super(destinationService, modelMapper);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    public MedidataEntitiesExportDTO exportEntities(){
        List<MedidataSponsor> sponsors = this.sponsorService.getEntities();
        List<MedidataApp> apps = this.appService.getEntities();
        List<MedidataTag> tags = this.tagService.getEntities();
        List<MedidataStudy> studies = this.studyService.getEntities();

        MedidataEntitiesExportDTO entities = new MedidataEntitiesExportDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)));

        return entities;
    }

    private List<MedidataStudyTransferDTO> mapStudiesToTransferDTO(Collection<MedidataStudy> studies) {
        List<MedidataStudyTransferDTO> transferDTOs = new ArrayList<>();
        for(MedidataStudy study : studies){
            MedidataStudyTransferDTO studyTransferDTO = this.modelMapper
                    .map(study, MedidataStudyTransferDTO.class);

            transferDTOs.add(studyTransferDTO);
        }

        return transferDTOs;
    }
}
