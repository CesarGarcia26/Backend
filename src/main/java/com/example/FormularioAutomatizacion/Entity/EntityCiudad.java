package com.example.FormularioAutomatizacion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ciudades")
public class EntityCiudad {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nombre", nullable = false)
        private String nombre;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "departamento_id", nullable = false)
        private EntityDepartament departamento;


        public EntityCiudad() {}

        public EntityCiudad(String nombre, EntityDepartament departamento) {
            this.nombre = nombre;
            this.departamento = departamento;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public EntityDepartament getDepartamento() { return departamento; }
        public void setDepartamento(EntityDepartament departamento) { this.departamento = departamento; }

}
