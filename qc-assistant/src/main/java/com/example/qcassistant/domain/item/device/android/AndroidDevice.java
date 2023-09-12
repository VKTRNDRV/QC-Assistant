package com.example.qcassistant.domain.item.device.android;

import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.enums.ShellType;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class AndroidDevice extends BaseDevice {

    public AndroidDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, OperatingSystem.ANDROID, connectorType, shellType, serial);
    }
}
