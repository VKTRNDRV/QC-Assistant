package com.example.qcassistant.domain.item.device.ios;

import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.enums.ShellType;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class IOSDevice extends BaseDevice {

    public IOSDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, OperatingSystem.IOS, connectorType, shellType, serial);
    }
}
