package com.springmotors.springmotorsapp.repositorios;

import com.springmotors.springmotorsapp.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface proveedorRepository extends JpaRepository<Proveedor, Long> {
}