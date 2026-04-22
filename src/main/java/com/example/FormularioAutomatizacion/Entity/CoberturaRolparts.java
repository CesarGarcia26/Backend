package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cobertura_rolparts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoberturaRolparts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cobertura;

    // Planes A - H
    private Double a;
    private Double b;
    private Double c;
    private Double d;
    private Double e;
    private Double f;
    private Double g;
    private Double h;

    // Campos opcionales (por si alguna cobertura cambia lógica futura)
    private Double valorFijo;
    private Double multiplicador;
    private Boolean requiereSalario;
    private Boolean usaTiers;
}
