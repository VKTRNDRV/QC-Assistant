package com.example.qcassistant.domain.dto.study.transfer;

import com.google.gson.annotations.Expose;
import jakarta.persistence.Column;

public class BaseStudyTransferDTO {

    @Expose
    private String name;

    @Expose
    private String folderURL;

    public String getName() {
        return name;
    }

    public BaseStudyTransferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public BaseStudyTransferDTO setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }
}
