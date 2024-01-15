package com.example.qcassistant.service.transfer;

import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.app.MedidataAppService;
import com.example.qcassistant.service.sponsor.MedidataSponsorService;
import com.example.qcassistant.service.study.MedidataStudyService;
import com.example.qcassistant.service.tag.MedidataTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
