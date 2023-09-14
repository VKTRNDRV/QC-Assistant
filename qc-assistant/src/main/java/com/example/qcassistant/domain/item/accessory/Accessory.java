package com.example.qcassistant.domain.item.accessory;

import com.example.qcassistant.domain.enums.item.ItemType;
import com.example.qcassistant.domain.item.BaseItem;

public class Accessory extends BaseItem {
    public Accessory(String name) {
        super(name, ItemType.ACCESSORY);
    }
}
