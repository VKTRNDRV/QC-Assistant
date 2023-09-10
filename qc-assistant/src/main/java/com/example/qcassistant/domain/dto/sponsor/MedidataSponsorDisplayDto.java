package com.example.qcassistant.domain.dto.sponsor;

public class MedidataSponsorDisplayDto {

    private Long id;

    private String name;

    private String areStudyNamesSimilar;

    public Long getId() {
        return id;
    }

    public MedidataSponsorDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedidataSponsorDisplayDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public MedidataSponsorDisplayDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
