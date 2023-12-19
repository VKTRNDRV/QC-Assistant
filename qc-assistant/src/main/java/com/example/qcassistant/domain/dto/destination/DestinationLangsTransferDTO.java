package com.example.qcassistant.domain.dto.destination;

public class DestinationLangsTransferDTO {

    private String destinations;

    private String languages;

    public String getDestinations() {
        return destinations;
    }

    public DestinationLangsTransferDTO setDestinations(String destinations) {
        this.destinations = destinations;
        return this;
    }

    public String getLanguages() {
        return languages;
    }

    public DestinationLangsTransferDTO setLanguages(String languages) {
        this.languages = languages;
        return this;
    }
}
