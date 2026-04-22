package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "covertura_PyC")
public class CoberturaPyC{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Boolean requiereSalario;
    private Boolean usaTiers;
    private String cobertura;

    private Long a;
    private Long b;
    private Long c;
    private Long d;
    private Long e;
    private Long f;
    private Long g;
    private Long h;
    private Long i;
    @Column(name = "valor_fijo")
    private Long valorFijo;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getRequiereSalario() { return requiereSalario; }
    public void setRequiereSalario(Boolean requiereSalario) { this.requiereSalario = requiereSalario; }

    public Boolean getUsaTiers() { return usaTiers; }
    public void setUsaTiers(Boolean usaTiers) { this.usaTiers = usaTiers; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public Long getA() { return a; }
    public void setA(Long a) { this.a = a; }

    public Long getB() { return b; }
    public void setB(Long b) { this.b = b; }

    public Long getC() { return c; }
    public void setC(Long c) { this.c = c; }

    public Long getD() { return d; }
    public void setD(Long d) { this.d = d; }

    public Long getE() { return e; }
    public void setE(Long e) { this.e = e; }

    public Long getF() { return f; }
    public void setF(Long f) { this.f = f; }

    public Long getG() { return g; }
    public void setG(Long g) { this.g = g; }

    public Long getH() { return h; }
    public void setH(Long h) { this.h = h; }

    public Long getI() { return i; }
    public void setI(Long i) { this.i = i; }

    public Long getValorFijo() { return valorFijo; }
    public void setValorFijo(Long valorFijo) { this.valorFijo = valorFijo; }
}
