package com.example.qcassistant.service.qc.orderParse;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.LanguageService;
import com.example.qcassistant.service.study.MedidataStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedidataOrderParseService extends ClinicalOrderParseService {

    private MedidataStudyService studyService;
    private DestinationService destinationService;

    @Autowired
    public MedidataOrderParseService(MedidataStudyService studyService, DestinationService destinationService, LanguageService languageService) {
        super(destinationService, languageService);
        this.studyService = studyService;
        this.destinationService = destinationService;
    }


    public MedidataOrder parseOrder(RawOrderInputDto inputDto) {
        validateInput(inputDto.getRawText());
        MedidataOrder order = new MedidataOrder();
        return null;
    }

    private void validateInput(String rawText) {

        // validate medidata order
    }
}
