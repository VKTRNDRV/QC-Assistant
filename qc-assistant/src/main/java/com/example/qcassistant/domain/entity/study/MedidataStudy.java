package com.example.qcassistant.domain.entity.study;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.persistence.*;

@Entity
@Table(name = "medidata_studies")
public class MedidataStudy extends BaseStudy{

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private MedidataSponsor sponsor;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private MedidataEnvironment environment;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_labels")
    private TrinaryBoolean containsTranslatedLabels;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_docs")
    private TrinaryBoolean containsTranslatedDocs;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_editable_welcome_letter")
    private TrinaryBoolean containsEditableWelcomeLetter;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_patient_device_ipad")
    private TrinaryBoolean isPatientDeviceIpad;

    @Enumerated(EnumType.STRING)
    @Column(name = "includes_headphones_styluses")
    private TrinaryBoolean includesHeadphonesStyluses;
}
