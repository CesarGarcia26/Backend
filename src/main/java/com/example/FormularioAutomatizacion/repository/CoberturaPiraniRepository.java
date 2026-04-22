package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.CoberturaPirani;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoberturaPiraniRepository extends JpaRepository<CoberturaPirani, Long> {

    // 🔹 Obtener todas las coberturas ordenadas por nombre
    List<CoberturaPirani> findAllByOrderByCoberturaAsc();

    // 🔹 Buscar por nombre exacto de cobertura
    Optional<CoberturaPirani> findByCoberturaIgnoreCase(String cobertura);

    // 🔹 Buscar coberturas que contengan una palabra
    List<CoberturaPirani> findByCoberturaContainingIgnoreCase(String nombre);

}
