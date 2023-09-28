package com.example.qcassistant.domain.dto.sponsor;

public class sponsorEditDto {

    private Long id;
    private String name;
    private String areStudyNamesSimilar;


    public Long getId() {
        return id;
    }

    public sponsorEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public sponsorEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public sponsorEditDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
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
