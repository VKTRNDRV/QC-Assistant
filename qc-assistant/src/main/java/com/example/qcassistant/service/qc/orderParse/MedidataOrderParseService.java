package com.example.qcassistant.service.qc.orderParse;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.item.device.android.phone.AndroidPhone;
import com.example.qcassistant.domain.item.device.android.phone.MedidataAndroidPhone;
import com.example.qcassistant.domain.item.device.ios.ipad.IPad;
import com.example.qcassistant.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistant.domain.item.device.ios.iphone.IPhone;
import com.example.qcassistant.domain.item.device.ios.iphone.MedidataIPhone;
import com.example.qcassistant.domain.item.document.Document;
import com.example.qcassistant.domain.order.DeviceRepository;
import com.example.qcassistant.domain.order.DocumentRepository;
import com.example.qcassistant.domain.order.MedidataOrder;
import com.example.qcassistant.exception.OrderParsingException;
import com.example.qcassistant.regex.MedidataOrderInputRegex;
import com.example.qcassistant.service.DestinationService;
import com.example.qcassistant.service.LanguageService;
import com.example.qcassistant.service.study.MedidataStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Collection<Language> requestedLanguages = super
                .getRequestedLanguages(segmentedInput);
        Destination destination = super.getDestination(segmentedInput);
        MedidataStudy study = this.getStudy(segmentedInput);
        MedidataSponsor sponsor = study.getSponsor();
        DocumentRepository documents = this.getDocuments(segmentedInput);
        DeviceRepository deviceRepository = this.getDevices(segmentedInput);

        MedidataOrder order = new MedidataOrder();
        order.setStudy(study)
                .setSponsor(sponsor)
                .setDocumentRepository(documents)
                .setOrderTermComments(segmentedInput
                        .getOrderTermComments())
                .setDestination(destination)
                .setRequestedLanguages(requestedLanguages)
                .setDeviceRepository(deviceRepository);
        return order;
    }

    private DeviceRepository getDevices(SegmentedOrderInput segmentedInput) {
        DeviceRepository devices = new DeviceRepository();
        String items = segmentedInput.getItemList();
        this.getIPhones(items).forEach(devices::addDevice);
        this.getIPads(items).forEach(devices::addDevice);
        this.getAndroidPhones(items).forEach(devices::addDevice);

        return devices;
    }

    private Collection<Device> getAndroidPhones(String items) {
        Collection<Device> phones = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedidataAndroidPhone phoneConst : MedidataAndroidPhone.values()){
            pattern = Pattern.compile(phoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedidataIPhone.SERIAL_GROUP_NAME);
                phones.add(new AndroidPhone(
                        phoneConst.getShortName(),
                        phoneConst.getConnectorType(),
                        serial));
            }
        }

        return phones;
    }

    private Collection<IPad> getIPads(String items) {
        Collection<IPad> iPads = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedidataIPad iPadConst : MedidataIPad.values()){
            pattern = Pattern.compile(iPadConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedidataIPhone.SERIAL_GROUP_NAME);
                iPads.add(new IPad(
                        iPadConst.getShortName(),
                        iPadConst.getConnectorType(),
                        serial));
            }
        }

        return iPads;
    }

    private Collection<IPhone> getIPhones(String items) {
        Collection<IPhone> iPhones = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedidataIPhone iPhoneConst : MedidataIPhone.values()){
            pattern = Pattern.compile(iPhoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedidataIPhone.SERIAL_GROUP_NAME);
                iPhones.add(new IPhone(
                        iPhoneConst.getShortName(),
                        iPhoneConst.getConnectorType(),
                        serial));
            }
        }

        return iPhones;
    }

    private DocumentRepository getDocuments(SegmentedOrderInput segmentedInput) {
        DocumentRepository documents = new DocumentRepository();
        String items = segmentedInput.getItemList();
        Pattern pattern = Pattern.compile(MedidataOrderInputRegex.DOCUMENT_REGEX);
        Matcher matcher = pattern.matcher(items);
        while (matcher.find()){
            Integer docCount = Integer.parseInt(matcher.group(
                            MedidataOrderInputRegex.DOC_COPIES_COUNT_GROUP));
            documents.addDocument(new Document(
                    MedidataOrderInputRegex.DOC_SHORTNAME, docCount));
        }

        pattern = Pattern.compile(MedidataOrderInputRegex.WELCOME_LETTER_REGEX);
        matcher = pattern.matcher(items);
        if(matcher.find()){
            documents.addDocument(new Document(
                    MedidataOrderInputRegex.WELCOME_LETTER_SHORTNAME, 1));
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
