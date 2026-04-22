package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EntityUser,Long> {
    Optional<EntityUser> findByUsername(String username);
}
