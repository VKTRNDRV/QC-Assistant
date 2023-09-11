package com.example.qcassistant.domain.item.device.ios;

import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class IOSDevice extends BaseDevice {

    public IOSDevice(){
        super.setOperatingSystem(OperatingSystem.IOS);
    }
}
