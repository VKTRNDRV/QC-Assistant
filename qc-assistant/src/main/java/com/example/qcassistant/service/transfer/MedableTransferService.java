package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistant.domain.entity.app.MedableApp;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.study.MedableStudy;
import com.example.qcassistant.domain.entity.tag.MedableTag;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.MedableAppService;
import com.example.qcassistant.service.sponsor.MedableSponsorService;
import com.example.qcassistant.service.study.MedableStudyService;
import com.example.qcassistant.service.tag.MedableTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedableTransferService extends BaseTransferService{

    private MedableSponsorService sponsorService;

    private MedableAppService appService;

    private MedableTagService tagService;

    private MedableStudyService studyService;

    public MedableTransferService(DestinationService destinationService,
                                  ModelMapper modelMapper,
                                  Gson gson,
                                  MedableSponsorService sponsorService,
                                  MedableAppService appService,
                                  MedableTagService tagService,
                                  MedableStudyService studyService) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    @Override
    public ClinicalEntitiesTransferDTO exportEntities(){
        List<MedableSponsor> sponsors = this.sponsorService.getEntities();
        List<MedableApp> apps = this.appService.getEntities();
        List<MedableTag> tags = this.tagService.getEntities();
        List<MedableStudy> studies = this.studyService.getEntities();

        ClinicalEntitiesTransferDTO entities = new ClinicalEntitiesTransferDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
//                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)))
        ;

        //TODO: FINISH STUDY MAPPING

        return entities;
    }

    @Override
    public void importEntities(ClinicalEntitiesTransferDTO entitiesJSON) {

    }

    @Override
    protected MedableAppService getAppService() {
        return this.appService;
    }

    @Override
    protected MedableSponsorService getSponsorService() {
        return this.sponsorService;
    }
}
