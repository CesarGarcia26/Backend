package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityInfoEmpresas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfoEmpresaRepository extends JpaRepository<EntityInfoEmpresas, Long> {
    Optional<EntityInfoEmpresas> findByNombreEmpresa(String nombreEmpresa);

}