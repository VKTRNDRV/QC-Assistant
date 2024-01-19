package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistant.domain.entity.app.IqviaApp;
import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.IqviaStudy;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.entity.tag.IqviaTag;
import com.example.qcassistant.domain.entity.tag.MedidataTag;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.IqviaAppService;
import com.example.qcassistant.service.sponsor.IqviaSponsorService;
import com.example.qcassistant.service.study.IqviaStudyService;
import com.example.qcassistant.service.tag.IqviaTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IqviaTransferService extends BaseTransferService{

    private IqviaSponsorService sponsorService;

    private IqviaAppService appService;

    private IqviaTagService tagService;

    private IqviaStudyService studyService;

    public IqviaTransferService(DestinationService destinationService,
                                ModelMapper modelMapper,
                                Gson gson,
                                IqviaSponsorService sponsorService,
                                IqviaAppService appService,
                                IqviaTagService tagService,
                                IqviaStudyService studyService) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    @Override
    public ClinicalEntitiesTransferDTO exportEntities(){
        List<IqviaSponsor> sponsors = this.sponsorService.getEntities();
        List<IqviaApp> apps = this.appService.getEntities();
        List<IqviaTag> tags = this.tagService.getEntities();
        List<IqviaStudy> studies = this.studyService.getEntities();

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
    protected IqviaAppService getAppService() {
        return this.appService;
    }

    @Override
    protected IqviaSponsorService getSponsorService() {
        return this.sponsorService;
    }
}
