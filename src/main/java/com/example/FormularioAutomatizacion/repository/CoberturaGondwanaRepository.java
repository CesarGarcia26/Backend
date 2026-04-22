package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.CoberturaGondwana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoberturaGondwanaRepository extends JpaRepository<CoberturaGondwana, Long> {

    // Ordenar por nombre de cobertura (opcional pero recomendado)
    List<CoberturaGondwana> findAllByOrderByCoberturaAsc();
}
