package com.springmotors.springmotorsapp.repositorios;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface usuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);
}