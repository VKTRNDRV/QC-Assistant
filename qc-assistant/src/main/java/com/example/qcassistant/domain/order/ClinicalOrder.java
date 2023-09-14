package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.entity.destination.Destination;
import com.example.qcassistant.domain.entity.destination.Language;
import com.example.qcassistant.domain.enums.OrderType;

import java.util.Collection;

public abstract class ClinicalOrder {

    private OrderType orderType;

    private Destination destination;

    private Collection<Language> requestedLanguages;

    private String orderTermComments;

    private DeviceRepository deviceRepository;

    private AccessoryRepository accessoryRepository;


    public Destination getDestination() {
        return destination;
    }

    public ClinicalOrder setDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public Collection<Language> getRequestedLanguages() {
        return requestedLanguages;
    }

    public ClinicalOrder setRequestedLanguages(Collection<Language> requestedLanguages) {
        this.requestedLanguages = requestedLanguages;
        return this;
    }

    public String getOrderTermComments() {
        return orderTermComments;
    }

    public ClinicalOrder setOrderTermComments(String orderTermComments) {
        this.orderTermComments = orderTermComments;
        return this;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public ClinicalOrder setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public ClinicalOrder setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public AccessoryRepository getAccessoryRepository() {
        return accessoryRepository;
    }

    public ClinicalOrder setAccessoryRepository(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
        return this;
    }
}
