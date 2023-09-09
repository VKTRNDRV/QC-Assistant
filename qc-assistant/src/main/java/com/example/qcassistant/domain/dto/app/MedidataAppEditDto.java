package com.example.qcassistant.domain.dto.app;

public class MedidataAppEditDto {

    private Long id;

    private String name;

    private String requiresCamera;


    public Long getId() {
        return id;
    }

    public MedidataAppEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedidataAppEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRequiresCamera() {
        return requiresCamera;
    }

    public MedidataAppEditDto setRequiresCamera(String requiresCamera) {
        this.requiresCamera = requiresCamera;
        return this;
    }
}
