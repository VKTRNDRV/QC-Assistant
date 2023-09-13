package com.example.qcassistant.domain.item.device.ios.iphone;

import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.ShellType;
import com.example.qcassistant.domain.item.device.ios.IOSDevice;

public class IPhone extends IOSDevice {
    public IPhone(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.PHONE, serial);
    }
}
