package com.example.qcassistant.domain.dto.sponsor;

public class MedidataSponsorEditDto {

    private Long id;
    private String name;
    private String areStudyNamesSimilar;


    public Long getId() {
        return id;
    }

    public MedidataSponsorEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedidataSponsorEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public MedidataSponsorEditDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
