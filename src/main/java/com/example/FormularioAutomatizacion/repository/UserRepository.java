package com.example.FormularioAutomatizacion.repository;

import com.example.FormularioAutomatizacion.Entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<EntityUser, Long> {
    Optional<EntityUser> findByUsername(String username);

    @Query("SELECT u FROM EntityUser u JOIN FETCH u.empresa WHERE u.username = :username")
    Optional<EntityUser> findByUsernameWithEmpresa(@Param("username") String username);
}