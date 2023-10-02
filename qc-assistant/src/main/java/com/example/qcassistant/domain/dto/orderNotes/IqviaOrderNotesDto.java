package com.example.qcassistant.domain.dto.orderNotes;

import com.example.qcassistant.domain.dto.study.info.IqviaStudyInfoDto;
import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.note.Note;

import java.util.ArrayList;
import java.util.Collection;

public class IqviaOrderNotesDto extends OrderNotesDto{


    protected Collection<Note> windowsNotes;
    private IqviaStudyInfoDto study;

    public IqviaOrderNotesDto(){
        this.windowsNotes = new ArrayList<>();
    }

    public IqviaStudyInfoDto getStudy() {
        return study;
    }

    public IqviaOrderNotesDto setStudy(IqviaStudyInfoDto study) {
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

    public boolean containsWindowsDevices(){
        return this.windowsNotes.size() > 0;
    }
}
