package com.example.qcassistant.domain.entity.destination;

import com.example.qcassistant.domain.entity.BaseEntity;
import com.example.qcassistant.domain.enums.PlugType;
import com.example.qcassistant.domain.enums.SimType;
import com.example.qcassistant.util.TrinaryBoolean;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @Column(nullable = false,
            unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "destinations_languages",
            joinColumns = { @JoinColumn(name = "destination_id") },
            inverseJoinColumns = { @JoinColumn(name = "language_id") }
    )
    private List<Language> languages;

    @Enumerated(EnumType.STRING)
    @Column(name = "plug_type")
    private PlugType plugType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sim_type")
    private SimType simType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_special_model")
    private TrinaryBoolean requiresSpecialModels;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_unused_devices")
    private TrinaryBoolean requiresUnusedDevices;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_invoice")
    private TrinaryBoolean requiresInvoice;
}
