package com.example.qcassistant.domain.item.device;

import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;

public interface Device {

    ConnectorType getConnectorType();

    OperatingSystem getOperatingSystem();

    ShellType getShellType();

    String getSerial();

    String getShortName();
}
