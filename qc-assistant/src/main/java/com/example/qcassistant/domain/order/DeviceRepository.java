package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.item.device.Device;

import java.util.ArrayList;
import java.util.Collection;

public class DeviceRepository {

    private Collection<Device> devices;

    public DeviceRepository(){
        this.devices = new ArrayList<>();
    }
}
