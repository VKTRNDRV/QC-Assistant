package com.example.qcassistant.domain.dto;

import com.example.qcassistant.domain.dto.device.DeviceNameSerialDto;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.note.Note;

import java.util.ArrayList;
import java.util.Collection;

public class OrderNotesDto {

    private Collection<DeviceNameSerialDto> devices;
    private Collection<Note> shellCheckNotes;
    private Collection<Note> documentationNotes;
    private Collection<Note> iosNotes;
    private Collection<Note> androidNotes;
    private Collection<Note> windowsNotes;


    public OrderNotesDto() {
        this.devices = new ArrayList<>();
        this.shellCheckNotes = new ArrayList<>();
        this.documentationNotes = new ArrayList<>();
        this.iosNotes = new ArrayList<>();
        this.androidNotes = new ArrayList<>();
        this.windowsNotes = new ArrayList<>();
    }

    public Collection<DeviceNameSerialDto> getDevices() {
        return devices;
    }

    public OrderNotesDto setDevices(Collection<DeviceNameSerialDto> devices) {
        this.devices = devices;
        return this;
    }

    public Collection<Note> getShellCheckNotes() {
        return shellCheckNotes;
    }

    public OrderNotesDto setShellCheckNotes(Collection<Note> shellCheckNotes) {
        this.shellCheckNotes = shellCheckNotes;
        return this;
    }

    public Collection<Note> getDocumentationNotes() {
        return documentationNotes;
    }

    public OrderNotesDto setDocumentationNotes(Collection<Note> documentationNotes) {
        this.documentationNotes = documentationNotes;
        return this;
    }

    public Collection<Note> getIosNotes() {
        return iosNotes;
    }

    public OrderNotesDto setIosNotes(Collection<Note> iosNotes) {
        this.iosNotes = iosNotes;
        return this;
    }

    public Collection<Note> getAndroidNotes() {
        return androidNotes;
    }

    public OrderNotesDto setAndroidNotes(Collection<Note> androidNotes) {
        this.androidNotes = androidNotes;
        return this;
    }

    public Collection<Note> getWindowsNotes() {
        return windowsNotes;
    }

    public OrderNotesDto setWindowsNotes(Collection<Note> windowsNotes) {
        this.windowsNotes = windowsNotes;
        return this;
    }

    public void addDevice(Device device){
        this.devices.add(new DeviceNameSerialDto()
                .setShortName(device.getShortName())
                .setSerial(device.getSerial()));
    }
}
