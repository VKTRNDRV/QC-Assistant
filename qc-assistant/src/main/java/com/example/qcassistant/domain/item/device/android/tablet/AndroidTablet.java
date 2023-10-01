package com.example.qcassistant.domain.item.device.android.tablet;

import com.example.qcassistant.domain.enums.item.ConnectorType;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.android.AndroidDevice;

public class AndroidTablet extends AndroidDevice {
    public AndroidTablet(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.TABLET, serial);
    }
}
