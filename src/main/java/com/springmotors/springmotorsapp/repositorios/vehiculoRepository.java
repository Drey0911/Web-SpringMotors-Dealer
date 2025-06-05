package com.springmotors.springmotorsapp.repositorios;

import com.springmotors.springmotorsapp.entidades.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehiculoRepository extends JpaRepository<Vehiculo, Long> {

}