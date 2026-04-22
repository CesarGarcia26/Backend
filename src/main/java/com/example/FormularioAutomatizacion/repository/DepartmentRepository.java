package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityDepartament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<EntityDepartament,Long> {
    List<EntityDepartament> findAllByOrderByNombreAsc();
}
