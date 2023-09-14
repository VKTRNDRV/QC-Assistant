package com.example.qcassistant.domain.item.device.ios;

import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class IOSDevice extends BaseDevice {

    public IOSDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, OperatingSystem.IOS, connectorType, shellType, serial);
    }
}
