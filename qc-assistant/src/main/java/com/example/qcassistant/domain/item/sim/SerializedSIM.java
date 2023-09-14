package com.example.qcassistant.domain.item.sim;

import com.example.qcassistant.domain.enums.item.ItemType;
import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.item.BaseItem;

public class SerializedSIM extends BaseItem {

    private SimType simType;
    private String serial;

    public SerializedSIM(String shortName, SimType simType, String serial) {
        super(shortName, ItemType.SIM);
        setSimType(simType);
        setSerial(serial);
    }


    public String getSerial() {
        return serial;
    }

    public SerializedSIM setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public SimType getSimType() {
        return simType;
    }

    public SerializedSIM setSimType(SimType simType) {
        this.simType = simType;
        return this;
    }
}
