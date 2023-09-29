package com.example.qcassistant.domain.dto.orderNotes;

import com.example.qcassistant.domain.dto.study.info.MedidataStudyInfoDto;

public class MedidataOrderNotesDto extends OrderNotesDto{

    private MedidataStudyInfoDto study;


    public MedidataStudyInfoDto getStudy() {
        return study;
    }

    public MedidataOrderNotesDto setStudy(MedidataStudyInfoDto study) {
        this.study = study;
        return this;
    }

    public boolean isStudyUnknown(){
        if(this.study == null){
            return true;
        }

        if(this.study.getName().equals("UNKNOWN")){
            return true;
        }

        return false;
    }
}
