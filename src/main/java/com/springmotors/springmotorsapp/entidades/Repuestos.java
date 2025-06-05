package com.springmotors.springmotorsapp.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Repuestos")
public class Repuestos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String nombre;
    private String descripcion;
    private String tipo; 
    private Double precio;
    private String estado;

    
    @Column(name = "foto", columnDefinition = "BYTEA")
    private byte[] foto;
    
    private String tipoImagen; 


@ManyToMany
@JoinTable(
    name = "VENTAS_REPUESTOS",
    joinColumns = @JoinColumn(name = "id_repuesto"),
    inverseJoinColumns = @JoinColumn(name = "id_usuario")
)

private Set<Usuarios> usuarios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
        public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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