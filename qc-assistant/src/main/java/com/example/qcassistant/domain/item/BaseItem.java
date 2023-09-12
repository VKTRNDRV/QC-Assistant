package com.example.qcassistant.domain.item;

import com.example.qcassistant.domain.enums.ItemType;
import com.example.qcassistant.exception.OrderParsingException;

public class BaseItem {
    private String shortName;

    private ItemType itemType;

    public BaseItem(String name, ItemType itemType){
        setShortName(name);
        setItemType(itemType);
    }

    public String getName() {
        return shortName;
    }

    public BaseItem setShortName(String shortName) {
        if(shortName == null || shortName.trim().isEmpty()){
            throw new OrderParsingException("Item name cannot be blank");
        }
        this.shortName = shortName.trim();
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
