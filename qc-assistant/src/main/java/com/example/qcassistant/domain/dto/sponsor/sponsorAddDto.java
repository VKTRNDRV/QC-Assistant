package com.example.qcassistant.domain.dto.sponsor;

public class sponsorAddDto {

    private String name;
    private String areStudyNamesSimilar;


    public String getName() {
        return name;
    }

    public sponsorAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public sponsorAddDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }


    public void trimStringFields() {
        if(this.name == null){
            return;
        }

        this.name = this.name.trim();
    }
}
