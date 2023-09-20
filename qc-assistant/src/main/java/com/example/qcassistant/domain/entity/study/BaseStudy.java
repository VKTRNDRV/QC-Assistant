package com.example.qcassistant.domain.entity.study;

import com.example.qcassistant.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseStudy extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column(name = "folder_url")
    private String folderURL;


    private static final String UNKNOWN = "UNKNOWN";

    public String getName() {
        return name;
    }

    public BaseStudy setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public BaseStudy setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public boolean isUnknown() {
        return name.equals(UNKNOWN);
    }
}
