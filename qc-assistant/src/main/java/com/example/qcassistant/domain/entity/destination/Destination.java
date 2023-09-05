package com.example.qcassistant.domain.entity.destination;

import com.example.qcassistant.domain.entity.BaseEntity;
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
    @Column(name = "requires_unused_devices")
    private TrinaryBoolean requiresUnusedDevices;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_invoice")
    private TrinaryBoolean requiresInvoice;


    public String getName() {
        return name;
    }

    public Destination setName(String name) {
        this.name = name;
        return this;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Destination setLanguages(List<Language> languages) {
        this.languages = languages;
        return this;
    }

    public TrinaryBoolean getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public Destination setRequiresUnusedDevices(TrinaryBoolean requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public TrinaryBoolean getRequiresInvoice() {
        return requiresInvoice;
    }

    public Destination setRequiresInvoice(TrinaryBoolean requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }
}
