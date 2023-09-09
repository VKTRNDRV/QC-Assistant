package com.example.qcassistant.domain.dto.app;

public class MedidataAppAddDto {

    private String name;

    private String requiresCamera;


    public String getName() {
        return name;
    }

    public MedidataAppAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRequiresCamera() {
        return requiresCamera;
    }

    public MedidataAppAddDto setRequiresCamera(String requiresCamera) {
        this.requiresCamera = requiresCamera;
        return this;
    }
}
