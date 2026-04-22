package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityEnfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfermedadRepository extends JpaRepository<EntityEnfermedad, Long> {

    List<EntityEnfermedad> findByActivoTrue();
}
