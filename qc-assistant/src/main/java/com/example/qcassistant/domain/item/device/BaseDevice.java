package com.example.qcassistant.domain.item.device;

import com.example.qcassistant.domain.enums.ItemType;
import com.example.qcassistant.domain.item.BaseItem;
import com.example.qcassistant.domain.enums.ConnectorType;
import com.example.qcassistant.domain.enums.OperatingSystem;
import com.example.qcassistant.domain.enums.ShellType;

public abstract class BaseDevice extends BaseItem implements Device{

    private OperatingSystem operatingSystem;
    private ConnectorType connectorType;
    private ShellType shellType;
    private String serial;


    public BaseDevice(){
        super.setItemType(ItemType.DEVICE);
    }


    @Override
    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public BaseDevice setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    @Override
    public ConnectorType getConnectorType() {
        return connectorType;
    }


    public BaseDevice setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
        return this;
    }

    @Override
    public ShellType getShellType() {
        return shellType;
    }


    public BaseDevice setShellType(ShellType shellType) {
        this.shellType = shellType;
        return this;
    }

    @Override
    public String getSerial() {
        return serial;
    }

    public BaseDevice setSerial(String serial) {
        this.serial = serial;
        return this;
    }
}
