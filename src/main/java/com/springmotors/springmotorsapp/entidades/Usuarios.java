package com.springmotors.springmotorsapp.entidades;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String rol;

    // Relacion Muchos A Muchos
    @ManyToMany(mappedBy = "usuarios")
    private Set<Vehiculo> vehiculos = new HashSet<>();

    @ManyToMany(mappedBy = "usuarios")
    private Set<Repuestos> repuestos = new HashSet<>();

   public Set<Repuestos> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(Set<Repuestos> repuestos) {
        this.repuestos = repuestos;
    }

   public Set<Vehiculo> getVehiculo() {
        return vehiculos;
    }

    public void setVehiculo(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

public String getNombreUsuario() {
    return nombreUsuario;
}

public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
}

public String getContrasena() {
    return contrasena;
}

public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
}

public String getRol() {
    return rol;
}

public void setRol(String rol) {
    this.rol = rol;
}

}
