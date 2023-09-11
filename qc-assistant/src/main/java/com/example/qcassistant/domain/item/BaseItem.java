package com.example.qcassistant.domain.item;

import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.enums.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

public abstract class BaseItem {
    private String name;

    private ItemType itemType;

    public String getName() {
        return name;
    }

    public BaseItem setName(String name) {
        this.name = name;
        return this;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public BaseItem setItemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }
}
