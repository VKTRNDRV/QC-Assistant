package com.example.qcassistant.domain.entity.study;

import com.example.qcassistant.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.BaseStudy;
import com.example.qcassistant.domain.entity.study.environment.BaseEnvironment;
import com.example.qcassistant.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistant.domain.entity.study.environment.MedableEnvironment;
import jakarta.persistence.*;

@Entity
@Table(name = "medable_studies")
public class MedableStudy extends BaseStudy {

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private MedableSponsor sponsor;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private MedableEnvironment environment;

    @Override
    public MedableSponsor getSponsor() {
        return sponsor;
    }

    @Override
    public MedableEnvironment getEnvironment() {
        return environment;
    }

    public MedableStudy setSponsor(MedableSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedableStudy setEnvironment(MedableEnvironment environment) {
        this.environment = environment;
        return this;
    }
}
