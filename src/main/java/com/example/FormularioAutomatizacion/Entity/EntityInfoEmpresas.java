package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "info_empresas")
public class EntityInfoEmpresas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "nombreEmpresa", nullable = false, length = 100)
    private String nombreEmpresa;

    @Column(name = "nit", unique = true, length = 20)
    private String nit;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "codigoAse", length = 20)
    private String codigoAse;

    public EntityInfoEmpresas() {}

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoAse() {
        return codigoAse;
    }

    public void setCodigoAse(String codigoAse) {
        this.codigoAse = codigoAse;
    }
}
