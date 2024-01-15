package com.example.qcassistant.domain.dto.sponsor;

import com.example.qcassistant.util.TrinaryBoolean;
import com.google.gson.annotations.Expose;

public class SponsorTransferDTO {

    @Expose
    private String name;

    @Expose
    private String areStudyNamesSimilar;

    public String getName() {
        return name;
    }

    public SponsorTransferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public SponsorTransferDTO setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
