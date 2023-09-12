package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;

public class MedidataOrder extends ClinicalOrder{

    private MedidataStudy study;

    private MedidataSponsor sponsor;

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
}
