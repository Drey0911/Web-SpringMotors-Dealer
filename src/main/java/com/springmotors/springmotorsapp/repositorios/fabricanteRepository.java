package com.springmotors.springmotorsapp.repositorios;

import com.springmotors.springmotorsapp.entidades.Fabricante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface fabricanteRepository extends JpaRepository<Fabricante, Long> {

    List<Fabricante> findByProveedorId(Long id);
}