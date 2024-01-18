package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.IqviaAppService;
import com.example.qcassistant.service.app.MedableAppService;
import com.example.qcassistant.service.sponsor.IqviaSponsorService;
import com.example.qcassistant.service.sponsor.MedableSponsorService;
import com.example.qcassistant.service.study.MedableStudyService;
import com.example.qcassistant.service.tag.MedableTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

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
