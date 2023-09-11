package com.example.qcassistant.domain.item.device;

import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.enums.ShellType;

public interface Device {

    ConnectorType getConnectorType();

    OperatingSystem getOperatingSystem();

    ShellType getShellType();

    String getSerial();

    String getName();
}
