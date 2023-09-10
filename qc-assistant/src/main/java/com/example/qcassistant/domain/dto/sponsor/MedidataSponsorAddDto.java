package com.example.qcassistant.domain.dto.sponsor;

public class MedidataSponsorAddDto {

    private String name;
    private String areStudyNamesSimilar;


    public String getName() {
        return name;
    }

    public MedidataSponsorAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public MedidataSponsorAddDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
