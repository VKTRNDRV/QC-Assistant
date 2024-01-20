package com.example.qcassistant.domain.dto.study.transfer;

import com.example.qcassistant.domain.dto.study.environment.transfer.MedableEnvironmentTransferDTO;
import com.google.gson.annotations.Expose;

public class MedableStudyTransferDTO extends BaseStudyTransferDTO {

    @Expose
    private MedableEnvironmentTransferDTO environment;

    public MedableEnvironmentTransferDTO getEnvironment() {
        return environment;
    }

    public MedableStudyTransferDTO setEnvironment(MedableEnvironmentTransferDTO environment) {
        this.environment = environment;
        return this;
    }
}
