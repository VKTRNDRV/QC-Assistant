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

    @Column(name = "folder_url")
    private String folderURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "includes_headphones_styluses")
    private TrinaryBoolean includesHeadphonesStyluses;
}
