package com.example.FormularioAutomatizacion.Dto;

import com.example.FormularioAutomatizacion.Entity.EntityDepartament;

public class DepartamentoDto{
    private Long id;
    private String nombre;

    public DepartamentoDto() {}

    public DepartamentoDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public DepartamentoDto(EntityDepartament departamento) {
        this.id = departamento.getId();
        this.nombre = departamento.getNombre();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
