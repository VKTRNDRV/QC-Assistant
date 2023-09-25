package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.enums.item.OperatingSystem;
import com.example.qcassistant.domain.enums.item.ShellType;
import com.example.qcassistant.domain.item.device.Device;

import java.util.*;

public class DeviceRepository {

    private List<Device> devices;

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

    public boolean containsIosDevices() {
        for(Device device : devices){
            if(device.getOperatingSystem().equals(OperatingSystem.IOS)){
                return true;
            }
        }

        return false;
    }

    public Optional<List<String>> getDuplicateSerials() {
        List<String> duplicates = new ArrayList<>();
        for (int i = 0; i < this.devices.size() - 1; i++) {
            String testSerial = this.devices.get(i).getSerial();

            for (int j = i + 1; j < this.devices.size(); j++) {
                String currentSerial = this.devices.get(j).getSerial();

                if(testSerial.equals(currentSerial)){
                    duplicates.add(testSerial);
                }
            }
        }

        if(duplicates.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(duplicates);
        }
    }
}
