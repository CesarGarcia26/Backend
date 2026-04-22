package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityCiudad;
import com.example.FormularioAutomatizacion.Entity.EntityDepartament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<EntityCiudad,Long> {
    List<EntityCiudad> findByDepartamentoIdOrderByNombreAsc(Long departamentoId);

}
