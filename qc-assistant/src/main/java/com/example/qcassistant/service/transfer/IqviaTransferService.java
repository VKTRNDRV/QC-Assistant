package com.example.qcassistant.service.transfer;

import com.example.qcassistant.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.IqviaAppService;
import com.example.qcassistant.service.sponsor.IqviaSponsorService;
import com.example.qcassistant.service.study.IqviaStudyService;
import com.example.qcassistant.service.tag.IqviaTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

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
