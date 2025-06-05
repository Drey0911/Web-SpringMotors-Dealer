package com.springmotors.springmotorsapp.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String tipo; // Sedán, SUV, Pickup, etc.
    private String transmision; // Manual/Automático
    private String combustible; // Gasolina, Diesel, Eléctrico, etc.
    private Double precio;
    private Integer kilometraje;
    private String descripcion;
    private String estado;

    

    @Column(name = "foto", columnDefinition = "BYTEA")
    private byte[] foto;
    
    private String tipoImagen; 


    @ManyToMany
    @JoinTable(
        name = "VENTAS_VEHICULOS",
        joinColumns = @JoinColumn(name = "id_vehiculo"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    
    private Set<Usuarios> usuarios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

     public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public Set<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
    
    // Método para obtener la imagen como Base64
    public String getFotoBase64() {
        if (foto == null || foto.length == 0) {
            return "";
        }
        return "data:" + tipoImagen + ";base64," + java.util.Base64.getEncoder().encodeToString(foto);
    }
}