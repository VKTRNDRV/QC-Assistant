package com.example.qcassistant.domain.entity.lineItem;

import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.enums.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseItem extends BaseEntity {

    @Column
    private String name;

    @Column
    private String regexPattern;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;
}
