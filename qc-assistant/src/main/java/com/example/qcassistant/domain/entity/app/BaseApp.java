package com.example.qcassistant.domain.entity.app;

import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseApp extends BaseEntity {

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_camera")
    private TrinaryBoolean requiresCamera;
}
