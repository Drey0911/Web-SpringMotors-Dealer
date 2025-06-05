package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Vehiculo;
import com.springmotors.springmotorsapp.entidades.Fabricante;
import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.repositorios.vehiculoRepository;

import jakarta.servlet.http.HttpSession;

import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import com.springmotors.springmotorsapp.repositorios.fabricanteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehiculos")
public class vehiculoController {  
    
    private final vehiculoRepository vehiculoRepository;
    private final usuariosRepository usuariosRepository;
    private final fabricanteRepository fabricanteRepository; 

    public vehiculoController(
        vehiculoRepository vehiculoRepository, 
        usuariosRepository usuariosRepository,
        fabricanteRepository fabricanteRepository
    ) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuariosRepository = usuariosRepository;
        this.fabricanteRepository = fabricanteRepository;
    }

@GetMapping("/listarVehiculos")
public String listarVehiculos(Model model) {
    model.addAttribute("vehiculos", vehiculoRepository.findAll());
    return "vehiculos/listarVehiculos";
}

@GetMapping("/catalogo")
public String mostrarCatalogo(Model model) {
    // Mantenemos tu código original
    model.addAttribute("vehiculos", vehiculoRepository.findAll());
    
    // Solo agregamos esta línea para cargar los fabricantes
    model.addAttribute("fabricantes", fabricanteRepository.findAll());
    
    return "vehiculos/catalogoVehiculos";
}

    @GetMapping("/formVehiculos")
    public String mostrarFormulario(Model model, @RequestParam(required = false) Long id) {
        Vehiculo vehiculo = id == null ? new Vehiculo() : vehiculoRepository.findById(id).orElse(new Vehiculo());
        
        // Obtener todos los usuarios
        List<Usuarios> todosUsuarios = usuariosRepository.findAll();
        
        // Obtener IDs de usuarios asociados al vehículo
        Set<Long> usuariosAsociados = vehiculo.getUsuarios().stream()
                .map(Usuarios::getId)
                .collect(Collectors.toSet());
        
        // Obtener todos los fabricantes
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("usuarios", todosUsuarios);
        model.addAttribute("usuariosAsociados", usuariosAsociados);
        model.addAttribute("fabricantes", fabricantes); // Añadir fabricantes al modelo
        return "vehiculos/formVehiculos";
    }

    @PostMapping("/guardar")
    public String guardarVehiculo(
            @ModelAttribute Vehiculo vehiculo,
            @RequestParam(value = "usuariosSeleccionados", required = false) List<Long> usuariosSeleccionados,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            @RequestParam("fabricanteId") Long fabricanteId) throws IOException { // Añadir fabricanteId
        
        // Procesar imagen
        if (!imagenFile.isEmpty()) {
            vehiculo.setFoto(imagenFile.getBytes());
            vehiculo.setTipoImagen(imagenFile.getContentType());
        } else if (vehiculo.getId() != null) {
            Vehiculo vehiculoExistente = vehiculoRepository.findById(vehiculo.getId()).orElse(null);
            if (vehiculoExistente != null) {
                vehiculo.setFoto(vehiculoExistente.getFoto());
                vehiculo.setTipoImagen(vehiculoExistente.getTipoImagen());
            }
        }

        // Asignar fabricante
        Fabricante fabricante = fabricanteRepository.findById(fabricanteId)
            .orElseThrow(() -> new IllegalArgumentException("Fabricante no encontrado"));
        vehiculo.setFabricante(fabricante);

        // Procesar usuarios seleccionados
        if (usuariosSeleccionados != null && !usuariosSeleccionados.isEmpty()) {
            Set<Usuarios> usuarios = usuariosSeleccionados.stream()
                    .map(id -> usuariosRepository.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            vehiculo.setUsuarios(usuarios);
        } else {
            vehiculo.setUsuarios(new HashSet<>());
        }

        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos/listarVehiculos?success=" + (vehiculo.getId() == null ? "create" : "update");
    }

    @GetMapping("/eliminar")
    public String eliminarVehiculo(@RequestParam Long id) {
        vehiculoRepository.deleteById(id);
        return "redirect:/vehiculos/listarVehiculos";
    }

 @PostMapping("/comprar")
@ResponseBody
public ResponseEntity<Map<String, Object>> comprarVehiculo(
        @RequestParam Long id,  // ID desde la URL
        @RequestBody Map<String, Object> requestData,
        HttpSession session) {
    
    Map<String, Object> response = new HashMap<>();
    
    try {
        Long userId = Long.parseLong(requestData.get("userId").toString());
        // Verificar que el userId de la sesión coincide con el enviado
        Long sessionUserId = (Long) session.getAttribute("userId");
        if (sessionUserId == null || !sessionUserId.equals(userId)) {
            response.put("success", false);
            response.put("message", "Usuario no autenticado");
            return ResponseEntity.status(401).body(response);
        }

        // Obtener usuario y vehículo
        Usuarios usuario = usuariosRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        
        // Relacionar usuario con vehículo
        vehiculo.getUsuarios().add(usuario);
        vehiculoRepository.save(vehiculo);
        
        response.put("success", true);
        response.put("message", "Compra realizada con éxito");
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        response.put("success", false);
        response.put("message", "Error al procesar la compra: " + e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
}
}