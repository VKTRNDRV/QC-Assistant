package com.example.qcassistant.service.qc.orderParse;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.item.document.Document;
import com.example.qcassistant.domain.order.DocumentRepository;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.exception.OrderParsingException;
import com.example.qcassistant.regex.MedidataOrderInputRegex;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.LanguageService;
import com.example.qcassistant.service.study.MedidataStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MedidataOrderParseService extends ClinicalOrderParseService {

    private MedidataStudyService studyService;
    private DestinationService destinationService;

    @Autowired
    public MedidataOrderParseService(MedidataStudyService studyService,
                                     DestinationService destinationService,
                                     LanguageService languageService) {
        super(destinationService, languageService);
        this.studyService = studyService;
        this.destinationService = destinationService;
    }


    public MedidataOrder parseOrder(RawOrderInputDto inputDto) {
        SegmentedOrderInput segmentedInput = new SegmentedOrderInput(
                inputDto.getParsedRawText());

        validateInput(segmentedInput);

        Destination destination = super.getDestination(segmentedInput);

        Collection<Language> requestedLanguages = super
                .getRequestedLanguages(segmentedInput);

        MedidataStudy study = this.getStudy(segmentedInput);

        MedidataSponsor sponsor = study.getSponsor();

        DocumentRepository documents = this.getDocuments(segmentedInput);

        MedidataOrder order = new MedidataOrder();
        order.setStudy(study)
                .setSponsor(sponsor)
                .setDocumentRepository(documents)
                .setOrderTermComments(segmentedInput
                        .getOrderTermComments())
                .setDestination(destination)
                .setRequestedLanguages(requestedLanguages);
        return order;
    }

    private DocumentRepository getDocuments(SegmentedOrderInput segmentedInput) {
        DocumentRepository documents = new DocumentRepository();
        Pattern pattern = Pattern.compile(MedidataOrderInputRegex.DOCUMENT_REGEX);
        Matcher matcher = pattern.matcher(segmentedInput.getItemList());
        while (matcher.find()){
            Integer docCount = Integer.parseInt(matcher.group(
                            MedidataOrderInputRegex.DOC_COPIES_COUNT_GROUP));
            documents.addDocument(new Document(
                    MedidataOrderInputRegex.DOC_SHORTNAME, docCount));
        }

        return documents;
    }

    private void validateInput(SegmentedOrderInput segmentedOrderInput) {

        Pattern pattern = Pattern.compile(MedidataOrderInputRegex
                .CLIENT_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(
                segmentedOrderInput.getBasicInfo());

        if(!matcher.find()){
            throw new OrderParsingException(
                    "Client Medidata not detected.");
        }
    }

    private MedidataStudy getStudy(SegmentedOrderInput segmentedInput) {
        Pattern studyPattern = Pattern.compile(
                MedidataOrderInputRegex.STUDY_REGEX);
        Matcher matcher = studyPattern.matcher(segmentedInput.getBasicInfo());
        String studyName;
        if(matcher.find()){
            studyName = matcher.group(
                    MedidataOrderInputRegex.STUDY_GROUP);
        }else{
            throw new OrderParsingException(
                    "Study Name could not be detected");
        }

        for (MedidataStudy study : this.studyService.getEntities()){
            if(study.getName().equals(studyName)){
                return study;
            }
        }

        return this.studyService.getUnknownStudy();
    }
}
