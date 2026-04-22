package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cobertura_maquel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoberturaMaquel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cobertura;

    // Planes A - D
    private Double a;
    private Double b;
    private Double c;
    private Double d;

    // Campos opcionales
    private Double valorFijo;
    private Double multiplicador;
    private Boolean requiereSalario;
    private Boolean usaTiers;
}
