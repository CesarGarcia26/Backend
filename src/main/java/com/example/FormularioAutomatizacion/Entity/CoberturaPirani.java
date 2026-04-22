package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "covertura_pirani")
public class CoberturaPirani implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cobertura", nullable = false)
    private String cobertura;

    @Column(name = "usa_tiers")
    private Boolean usaTiers = true;

    @Column(name = "requiere_salario")
    private Boolean requiereSalario = false;

    @Column(name = "a")
    private Long a;

    @Column(name = "b")
    private Long b;

    @Column(name = "c")
    private Long c;

    @Column(name = "d")
    private Long d;

    @Column(name = "e")
    private Long e;

    @Column(name = "f")
    private Long f;

    @Column(name = "g")
    private Long g;

    @Column(name = "h")
    private Long h;

    @Column(name = "i")
    private Long i;

    @Column(name = "j")
    private Long j;

    @Column(name = "k")
    private Long k;

    @Column(name = "l")
    private Long l;

    @Column(name = "m")
    private Long m;

    @Column(name = "n")
    private Long n;

    @Column(name = "o")
    private Long o;

    @Column(name = "p")
    private Long p;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUsaTiers() {
        return usaTiers;
    }

    public void setUsaTiers(Boolean usaTiers) {
        this.usaTiers = usaTiers;
    }

    public Boolean getRequiereSalario() {
        return requiereSalario;
    }

    public void setRequiereSalario(Boolean requiereSalario) {
        this.requiereSalario = requiereSalario;
    }

    public Long getA() {
        return a;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public Long getD() {
        return d;
    }

    public void setD(Long d) {
        this.d = d;
    }

    public Long getE() {
        return e;
    }

    public void setE(Long e) {
        this.e = e;
    }

    public Long getF() {
        return f;
    }

    public void setF(Long f) {
        this.f = f;
    }

    public Long getG() {
        return g;
    }

    public void setG(Long g) {
        this.g = g;
    }

    public Long getH() {
        return h;
    }

    public void setH(Long h) {
        this.h = h;
    }

    public Long getI() {
        return i;
    }

    public void setI(Long i) {
        this.i = i;
    }

    public Long getJ() {
        return j;
    }

    public void setJ(Long j) {
        this.j = j;
    }

    public Long getK() {
        return k;
    }

    public void setK(Long k) {
        this.k = k;
    }

    public Long getL() {
        return l;
    }

    public void setL(Long l) {
        this.l = l;
    }

    public Long getM() {
        return m;
    }

    public void setM(Long m) {
        this.m = m;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public Long getO() {
        return o;
    }

    public void setO(Long o) {
        this.o = o;
    }

    public Long getP() {
        return p;
    }

    public void setP(Long p) {
        this.p = p;
    }
}
