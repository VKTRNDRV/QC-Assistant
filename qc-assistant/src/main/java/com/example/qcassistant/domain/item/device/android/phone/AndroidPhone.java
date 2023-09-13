package com.example.qcassistant.domain.item.device.android.phone;

import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.ShellType;
import com.example.qcassistant.domain.item.device.android.AndroidDevice;

public class AndroidPhone extends AndroidDevice {
    public AndroidPhone(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.PHONE, serial);
    }
}
