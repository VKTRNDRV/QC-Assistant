package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.Device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DeviceRepository {

    private Collection<Device> devices;

    public DeviceRepository(){
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device){
        this.devices.add(device);
    }

    public Collection<Device> getDevices(){
        return Collections.unmodifiableCollection(devices);
    }

    public boolean containsAndroidDevices() {
        for(Device device : devices){
            if(device.getOperatingSystem()
                    .equals(OperatingSystem.ANDROID)){
                return true;
            }
        }

        return false;
    }

    public boolean containsTablets() {
        for(Device device : devices){
            if(device.getShellType().equals(ShellType.TABLET)){
                return true;
            }
        }

        return false;
    }

    public boolean containsDevice(String shortName){
        for(Device device : devices){
            if(device.getShortName().equals(shortName)){
                return true;
            }
        }

        return false;
    }

    public boolean containsPhones() {
        for(Device device : devices){
            if(device.getShellType().equals(ShellType.PHONE)){
                return true;
            }
        }

        return false;
    }

    public int count(){
        return devices.size();
    }
}
