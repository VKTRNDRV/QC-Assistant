package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.study.MedableStudy;

public class MedableOrder extends ClinicalOrder{

    private MedableStudy study;

    private MedableSponsor sponsor;

    private SimRepository simRepository;


    @Override
    public boolean isStudyUnknown() {
        if(this.study == null){
            return true;
        }

        return this.study.isUnknown();
    }
}
