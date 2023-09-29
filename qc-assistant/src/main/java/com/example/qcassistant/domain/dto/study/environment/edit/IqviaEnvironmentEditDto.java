package com.example.qcassistant.domain.dto.study.environment.edit;

import java.util.List;

public class IqviaEnvironmentEditDto {

    private Long id;
    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private List<String> patientApps;
    private List<String> siteApps;



    public Long getId() {
        return id;
    }

    public IqviaEnvironmentEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public IqviaEnvironmentEditDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public IqviaEnvironmentEditDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public IqviaEnvironmentEditDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public IqviaEnvironmentEditDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
