package com.example.qcassistant.domain.item.device.medical;

import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.BaseDevice;

public class MedicalDevice extends BaseDevice {
    public MedicalDevice(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, OperatingSystem.OTHER, connectorType, ShellType.MEDICAL, serial);
    }
}
