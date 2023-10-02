package com.example.qcassistant.domain.dto.orderNotes;

import com.example.qcassistant.domain.dto.study.info.MedableStudyInfoDto;
import com.example.qcassistant.domain.entity.BaseEntity;

public class MedableOrderNotesDto extends OrderNotesDto{

    private MedableStudyInfoDto study;


    public MedableStudyInfoDto getStudy() {
        return study;
    }

    public MedableOrderNotesDto setStudy(MedableStudyInfoDto study) {
        this.study = study;
        return this;
    }

    public boolean isStudyUnknown(){
        if(this.study == null){
            return true;
        }

        if(this.study.getName().equals(BaseEntity.UNKNOWN)){
            return true;
        }

        return false;
    }
}
