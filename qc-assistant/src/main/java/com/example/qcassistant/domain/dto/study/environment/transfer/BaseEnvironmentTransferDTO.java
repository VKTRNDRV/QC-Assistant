package com.example.qcassistant.domain.dto.study.environment.transfer;

import com.google.gson.annotations.Expose;

public class BaseEnvironmentTransferDTO {

    @Expose
    private String isSitePatientSeparated;

    @Expose
    private String isDestinationSeparated;

    @Expose
    private String isOsSeparated;



    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public BaseEnvironmentTransferDTO setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public BaseEnvironmentTransferDTO setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public BaseEnvironmentTransferDTO setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
