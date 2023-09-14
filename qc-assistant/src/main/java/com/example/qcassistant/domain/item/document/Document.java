package com.example.qcassistant.domain.item.document;

import com.example.qcassistant.domain.enums.item.ItemType;
import com.example.qcassistant.domain.item.BaseItem;

public class Document extends BaseItem {

    private Integer copiesCount;

    public Document(String name, Integer copiesCount) {
        super(name, ItemType.DOCUMENT);
        setCopiesCount(copiesCount);
    }


    public Integer getCopiesCount() {
        return copiesCount;
    }

    public Document setCopiesCount(Integer copiesCount) {
        this.copiesCount = copiesCount;
        return this;
    }
}
