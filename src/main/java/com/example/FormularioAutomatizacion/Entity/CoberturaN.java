package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "covertura_N")
public class CoberturaN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Boolean requiereSalario;
    private String cobertura;
    private Integer multiplicador;
    @Column(name = "valor_fijo")
    private Long valorFijo;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getRequiereSalario() { return requiereSalario; }
    public void setRequiereSalario(Boolean requiereSalario) { this.requiereSalario = requiereSalario; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public Integer getMultiplicador() { return multiplicador; }
    public void setMultiplicador(Integer multiplicador) { this.multiplicador = multiplicador; }

    public Long getValorFijo() { return valorFijo; }
    public void setValorFijo(Long valorFijo) { this.valorFijo = valorFijo; }
}
