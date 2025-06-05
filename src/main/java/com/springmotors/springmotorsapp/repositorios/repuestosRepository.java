package com.springmotors.springmotorsapp.repositorios;

import com.springmotors.springmotorsapp.entidades.Repuestos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repuestosRepository extends JpaRepository<Repuestos, Long> {
}