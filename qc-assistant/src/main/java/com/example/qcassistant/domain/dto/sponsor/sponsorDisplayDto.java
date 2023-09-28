package com.example.qcassistant.domain.dto.sponsor;

public class sponsorDisplayDto {

    private Long id;

    private String name;

    private String areStudyNamesSimilar;

    public Long getId() {
        return id;
    }

    public sponsorDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public sponsorDisplayDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public sponsorDisplayDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
