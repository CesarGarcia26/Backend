package com.example.FormularioAutomatizacion.Dto;

import com.example.FormularioAutomatizacion.Entity.EntityCiudad;


public class CiudadDto {
    private Long id;
    private String nombre;
    private Long departamentoId;

    public CiudadDto() {}

    public CiudadDto(Long id, String nombre, Long departamentoId) {
        this.id = id;
        this.nombre = nombre;
        this.departamentoId = departamentoId;
    }

    public CiudadDto(EntityCiudad ciudad) {
        this.id = ciudad.getId();
        this.nombre = ciudad.getNombre();
        this.departamentoId = ciudad.getDepartamento().getId();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Long getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(Long departamentoId) { this.departamentoId = departamentoId; }
}