package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.entidades.Vehiculo;
import com.springmotors.springmotorsapp.entidades.Repuestos;
import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class ProfileController {

    private final usuariosRepository usuariosRepository;

    public ProfileController(usuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

@GetMapping("/perfil")
public String mostrarPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    String username = userDetails.getUsername();
    Usuarios usuario = usuariosRepository.findByNombreUsuario(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    model.addAttribute("username", username);
    model.addAttribute("role", userDetails.getAuthorities().iterator().next().getAuthority());
    
    // Cargar vehículos con todos sus datos
    Set<Vehiculo> vehiculosComprados = usuario.getVehiculo();
    // Cargar repuestos con todos sus datos
    Set<Repuestos> repuestosComprados = usuario.getRepuestos();
    
    model.addAttribute("vehiculos", vehiculosComprados);
    model.addAttribute("repuestos", repuestosComprados);
    
    return "auth/perfil";
}
}