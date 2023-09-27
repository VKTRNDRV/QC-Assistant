package com.example.qcassistant.domain.entity.study.environment;

import com.example.qcassistant.domain.entity.app.MedidataApp;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medidata_environments")
public class MedidataEnvironment extends BaseEnvironment{

    @Enumerated(EnumType.STRING)
    @Column(name = "is_legacy")
    private TrinaryBoolean isLegacy;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_environments_patient_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedidataApp> patientApps;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_environments_site_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedidataApp> siteApps;



    public TrinaryBoolean getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironment setIsLegacy(TrinaryBoolean isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    @Override
    public List<MedidataApp> getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironment setPatientApps(List<MedidataApp> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    @Override
    public List<MedidataApp> getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironment setSiteApps(List<MedidataApp> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
