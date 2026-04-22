package com.example.FormularioAutomatizacion.Dto;

public class EnfermedaDto {

    private Long id;
    private String nombre;

    // ✅ Constructor vacío OBLIGATORIO
    public EnfermedaDto() {
    }

    public EnfermedaDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
