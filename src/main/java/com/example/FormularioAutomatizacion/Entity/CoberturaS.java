package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "covertura_S")
public class CoberturaS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cobertura;
    @Column(name = "valor_fijo")
    private Long valorFijo;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public Long getValorFijo() { return valorFijo; }
    public void setValorFijo(Long valorFijo) { this.valorFijo = valorFijo; }
}
