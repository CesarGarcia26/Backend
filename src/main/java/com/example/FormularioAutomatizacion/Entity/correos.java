package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empresa_correos")
public class correos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correo")
    private Long idCorreo;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(nullable = false)
    private String correo;

    // ======= GETTERS =======

    public Long getIdCorreo() {
        return idCorreo;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public String getCorreo() {
        return correo;
    }

    // ======= SETTERS =======

    public void setIdCorreo(Long idCorreo) {
        this.idCorreo = idCorreo;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
