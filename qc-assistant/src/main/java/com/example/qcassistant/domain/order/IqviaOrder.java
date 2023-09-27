package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.study.IqviaStudy;

public class IqviaOrder extends ClinicalOrder{

    private IqviaStudy study;

    private IqviaSponsor sponsor;

    private SimRepository simRepository;

    private DocumentRepository documentRepository;

    @Override
    public boolean isStudyUnknown() {
        if(this.study == null){
            return true;
        }

        return this.study.isUnknown();
    }
}
