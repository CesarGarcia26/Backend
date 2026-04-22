package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamentos")
public class EntityDepartament {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nombre", nullable = false)
        private String nombre;

        @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<EntityCiudad> ciudades = new ArrayList<>();

        // Constructores
        public EntityDepartament() {
        }

        public EntityDepartament(String nombre) {
            this.nombre = nombre;
        }

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public List<EntityCiudad> getCiudades() {
            return ciudades;
        }

        public void setCiudades(List<EntityCiudad> ciudades) {
            this.ciudades = ciudades;
        }


}
