package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.MedidataStudy;

public class MedidataOrder extends ClinicalOrder{

    private MedidataStudy study;

    private MedidataSponsor sponsor;


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
}
