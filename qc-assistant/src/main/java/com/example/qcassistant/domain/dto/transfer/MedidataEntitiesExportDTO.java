package com.example.qcassistant.domain.dto.transfer;

import com.example.qcassistant.domain.dto.app.AppTransferDTO;
import com.example.qcassistant.domain.dto.sponsor.SponsorTransferDTO;
import com.example.qcassistant.domain.dto.study.transfer.MedidataStudyTransferDTO;
import com.example.qcassistant.domain.dto.tag.TagTransferDTO;

import java.util.List;

public class MedidataEntitiesExportDTO {

    private String tags;

    private String studies;

    private String sponsors;

    private String apps;



    public String getTags() {
        return tags;
    }

    public MedidataEntitiesExportDTO setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getStudies() {
        return studies;
    }

    public MedidataEntitiesExportDTO setStudies(String studies) {
        this.studies = studies;
        return this;
    }

    public String getSponsors() {
        return sponsors;
    }

    public MedidataEntitiesExportDTO setSponsors(String sponsors) {
        this.sponsors = sponsors;
        return this;
    }

    public String getApps() {
        return apps;
    }

    public MedidataEntitiesExportDTO setApps(String apps) {
        this.apps = apps;
        return this;
    }
}
