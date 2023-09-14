package com.example.qcassistant.domain.item;

import com.example.qcassistant.domain.enums.item.ItemType;
import com.example.qcassistant.exception.OrderParsingException;

public class BaseItem {
    private String shortName;

    private ItemType itemType;

    public BaseItem(String shortName, ItemType itemType){
        setShortName(shortName);
        setItemType(itemType);
    }

    public String getShortName() {
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
