package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cobertura_gondwana")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoberturaGondwana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cobertura;

    // Planes A - S (19 planes)
    private Double a;
    private Double b;
    private Double c;
    private Double d;
    private Double e;
    private Double f;
    private Double g;
    private Double h;
    private Double i;
    private Double j;
    private Double k;
    private Double l;
    private Double m;
    private Double n;
    private Double o;
    private Double p;
    private Double q;
    private Double r;
    private Double s;

    // Campos opcionales (estructura estándar de tu sistema)
    private Double valorFijo;
    private Double multiplicador;
    private Boolean requiereSalario;
    private Boolean usaTiers;
}
