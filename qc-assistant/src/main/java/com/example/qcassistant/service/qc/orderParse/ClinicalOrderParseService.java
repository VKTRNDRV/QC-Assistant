package com.example.qcassistant.service.qc.orderParse;

import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public abstract class ClinicalOrderParseService {

    protected DestinationService destinationService;
    protected LanguageService languageService;

    @Autowired
    protected ClinicalOrderParseService(DestinationService destinationService, LanguageService languageService) {
        this.destinationService = destinationService;
        this.languageService = languageService;
    }

    protected Destination getDestination(String shippingInstructions){

        return null;
    }

    protected Collection<Language> getRequestedLanguages(String orderTermComments){

        return null;
    }
}
