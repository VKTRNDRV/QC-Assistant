package com.example.qcassistant.domain.item.sim;

import com.example.qcassistant.domain.enums.item.SimType;
import com.example.qcassistant.domain.item.device.Device;

public enum MedidataSIM {

    VF_GLOBAL("Medidata\\s*VF", SimType.VF_GLOBAL),
    SIMON_IOT("Medidata\\s*Simon\\s*TMO", SimType.SIMON_IOT);
    private String regexPattern;
    private SimType simType;

    MedidataSIM(String regexPattern, SimType simType){
        this.regexPattern = regexPattern;
        this.simType = simType;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public SimType getSimType() {
        return simType;
    }
}
