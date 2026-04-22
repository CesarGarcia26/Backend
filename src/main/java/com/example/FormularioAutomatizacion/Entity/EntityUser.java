package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class EntityUser {

    @Id
    private Long id;

    private String username;
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id_empresa")
    private EntityInfoEmpresas empresa;

    public EntityUser() {}


    public EntityUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public EntityInfoEmpresas getEmpresa() {
        return empresa;
    }
    public void setEmpresa(EntityInfoEmpresas empresa) {
        this.empresa = empresa;
    }
}