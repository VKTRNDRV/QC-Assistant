package com.example.qcassistant.domain.item.device.android;

import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class AndroidDevice extends BaseDevice {

    public AndroidDevice(){
        super.setOperatingSystem(OperatingSystem.ANDROID);
    }
}
