package com.example.qcassistant.domain.dto.study.environment.transfer;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MedidataEnvironmentTransferDTO extends BaseEnvironmentTransferDTO{

    @Expose
    private String isLegacy;

    @Expose
    private List<String> patientApps;

    @Expose
    private List<String> siteApps;



    public String getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironmentTransferDTO setIsLegacy(String isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironmentTransferDTO setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironmentTransferDTO setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
