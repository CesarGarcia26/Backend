package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.CoberturaAcrrin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoberturaAcrrinRepository extends JpaRepository<CoberturaAcrrin, Long> {
}
