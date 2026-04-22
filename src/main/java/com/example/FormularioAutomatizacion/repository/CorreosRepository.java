package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.correos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorreosRepository extends JpaRepository<correos, Long> {
    List<correos> findByIdEmpresa(Long idEmpresa);
}
