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
}
