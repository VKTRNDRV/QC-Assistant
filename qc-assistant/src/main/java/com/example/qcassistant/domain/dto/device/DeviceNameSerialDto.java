package com.example.qcassistant.domain.dto.device;

public class DeviceNameSerialDto {

    private String shortName;
    private String serial;



    public String getShortName() {
        return shortName;
    }

    public DeviceNameSerialDto setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getSerial() {
        return serial;
    }

    public DeviceNameSerialDto setSerial(String serial) {
        this.serial = serial;
        return this;
    }
}
