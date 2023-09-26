package com.example.qcassistant.domain.dto.orderNotes;

import com.example.qcassistant.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistant.domain.item.device.Device;
import com.example.qcassistant.domain.note.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class OrderNotesDto {

    protected Collection<ItemNameSerialDto> items;
    protected Collection<Note> shellCheckNotes;
    protected Collection<Note> documentationNotes;
    protected Collection<Note> iosNotes;
    protected Collection<Note> androidNotes;

//    protected Collection<Note> windowsNotes;


    public OrderNotesDto() {
        this.items = new ArrayList<>();
        this.shellCheckNotes = new ArrayList<>();
        this.documentationNotes = new ArrayList<>();
        this.iosNotes = new ArrayList<>();
        this.androidNotes = new ArrayList<>();
//        this.windowsNotes = new ArrayList<>();
    }

    public Collection<ItemNameSerialDto> getItems() {
        return items;
    }

    public OrderNotesDto setItems(Collection<ItemNameSerialDto> items) {
        this.items = items;
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

//    public Collection<Note> getWindowsNotes() {
//        return windowsNotes;
//    }
//
//    public OrderNotesDto setWindowsNotes(Collection<Note> windowsNotes) {
//        this.windowsNotes = windowsNotes;
//        return this;
//    }

    public void addDevice(Device device){
        this.items.add(new ItemNameSerialDto()
                .setShortName(device.getShortName())
                .setSerial(device.getSerial()));
    }

    public boolean containsIosDevices(){
        return this.iosNotes.size() > 0;
    }

    public boolean containsAndroidDevices(){
        return this.androidNotes.size() > 0;
    }

//    public boolean containsWindowsDevices(){
//        return this.windowsNotes.size() > 0;
//    }

    public void sortItems() {
        this.setItems(items.stream()
                .sorted((i1,i2) -> i1.getShortName()
                        .compareTo(i2.getShortName()))
                .collect(Collectors.toList()));
    }
}
