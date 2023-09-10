package com.example.qcassistant.domain.dto.study;

public class MedidataStudyDisplayDto {

    private Long id;

    private String sponsor;

    private String name;


    public Long getId() {
        return id;
    }

    public MedidataStudyDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public MedidataStudyDisplayDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedidataStudyDisplayDto setName(String name) {
        this.name = name;
        return this;
    }
}
