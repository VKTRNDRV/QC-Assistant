package com.example.qcassistant.domain.item.device.ios.ipad;

import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.ios.IOSDevice;

public class IPad extends IOSDevice {
    public IPad(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.TABLET, serial);
    }
}
