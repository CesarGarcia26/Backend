package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.CoberturaMaquel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoberturaMaquelRepository extends JpaRepository<CoberturaMaquel, Long> {
}
