package com.example.qcassistant.service.orderParse;

import com.example.qcassistant.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.enums.OrderType;
import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.item.accessory.Accessory;
import com.example.qcassistant.domain.item.accessory.MedidataAccessory;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.item.device.android.phone.AndroidPhone;
import com.example.qcassistant.domain.item.device.android.phone.MedidataAndroidPhone;
import com.example.qcassistant.domain.item.device.ios.ipad.IPad;
import com.example.qcassistant.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistant.domain.item.device.ios.iphone.IPhone;
import com.example.qcassistant.domain.item.device.ios.iphone.MedidataIPhone;
import com.example.qcassistant.domain.item.document.Document;
import com.example.qcassistant.domain.item.sim.MedidataSIM;
import com.example.qcassistant.domain.order.AccessoryRepository;
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

        OrderType orderType = super.getOrderType(segmentedInput);
        Collection<Language> requestedLanguages = super
                .getRequestedLanguages(segmentedInput);
        Destination destination = super.getDestination(segmentedInput);
        MedidataStudy study = this.getStudy(segmentedInput);
        MedidataSponsor sponsor = study.getSponsor();
        DocumentRepository documents = this.getDocuments(segmentedInput);
        SimType includedSimsType = this.getIncludedSimsType(segmentedInput);
        DeviceRepository devices = this.getDevices(segmentedInput);
        AccessoryRepository accessories = this.getAccessories(segmentedInput);

        MedidataOrder order = new MedidataOrder();
        order.setStudy(study)
                .setSponsor(sponsor)
                .setSimType(includedSimsType)
                .setDocumentRepository(documents)
                .setOrderTermComments(segmentedInput
                        .getOrderTermComments())
                .setDestination(destination)
                .setRequestedLanguages(requestedLanguages)
                .setDeviceRepository(devices)
                .setAccessoryRepository(accessories)
                .setOrderType(orderType);
        return order;
    }

    private AccessoryRepository getAccessories(SegmentedOrderInput segmentedInput) {
        AccessoryRepository accessories = new AccessoryRepository();
        String items = segmentedInput.getItemList();
        Pattern pattern;
        Matcher matcher;
        for(MedidataAccessory accessoryConst : MedidataAccessory.values()){
            pattern = Pattern.compile(accessoryConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                accessories.addAccessory(new Accessory(
                        accessoryConst.getShortName()));
            }
        }

        return accessories;
    }

    private SimType getIncludedSimsType(SegmentedOrderInput segmentedInput) {
        String items = segmentedInput.getItemList();
        Pattern pattern;
        Matcher matcher;
        for(MedidataSIM simConst : MedidataSIM.values()){
            pattern = Pattern.compile(simConst.getRegexPattern());
            matcher = pattern.matcher(items);
            if(matcher.find()){
                return simConst.getSimType();
            }
        }

        return SimType.NONE;
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

    private Collection<Device> getIPads(String items) {
        Collection<Device> iPads = new ArrayList<>();
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

    private Collection<Device> getIPhones(String items) {
        Collection<Device> iPhones = new ArrayList<>();
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
        super.validateOrderType(segmentedOrderInput);
        this.validateClient(segmentedOrderInput);
    }

    private void validateClient(SegmentedOrderInput segmentedOrderInput) {
        Pattern pattern = Pattern.compile(MedidataOrderInputRegex
                .CLIENT_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(
                segmentedOrderInput.getItemList());

        if(!matcher.find()){
            throw new OrderParsingException(
                    "Client Medidata not detected.");
        }
    }

    private MedidataStudy getStudy(SegmentedOrderInput segmentedInput) {
        String studyNameRange = super.getStudyRangeString(segmentedInput);

        for (MedidataStudy study : this.studyService.getEntities()){
            if(studyNameRange.contains(study.getName())){
                return study;
            }
        }

        return this.studyService.getUnknownStudy();
    }
}
