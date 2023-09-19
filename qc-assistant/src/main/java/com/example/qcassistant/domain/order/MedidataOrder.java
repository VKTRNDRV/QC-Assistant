package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;
import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistant.regex.MedidataOrderInputRegex;
import com.example.qcassistant.util.TrinaryBoolean;

public class MedidataOrder extends ClinicalOrder{
    private MedidataStudy study;
    private MedidataSponsor sponsor;
    private SimType simType;
    private DocumentRepository documentRepository;



    public MedidataStudy getStudy() {
        return study;
    }

    public MedidataOrder setStudy(MedidataStudy study) {
        this.study = study;
        return this;
    }

    public MedidataSponsor getSponsor() {
        return sponsor;
    }

    public MedidataOrder setSponsor(MedidataSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public MedidataOrder setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        return this;
    }

    public SimType getSimType() {
        return simType;
    }

    public MedidataOrder setSimType(SimType simType) {
        this.simType = simType;
        return this;
    }

    public boolean containsPatientDevices(){
        return getDeviceRepository().containsPhones();
    }

    public boolean containsSiteDevices() {
        if(study.getIsPatientDeviceIpad()
                .equals(TrinaryBoolean.TRUE)){
            return orderTermComments.contains(
                    MedidataOrderInputRegex.MULTI_USER);
        }else {
            for (MedidataIPad iPadConst : MedidataIPad.values()) {
                if (!iPadConst.getShortName().equals(MedidataIPad.MINI.getShortName()) &&
                        deviceRepository.containsDevice(iPadConst.getShortName())) {
                    return true;
                }
            }

            return false;
        }
    }
}
