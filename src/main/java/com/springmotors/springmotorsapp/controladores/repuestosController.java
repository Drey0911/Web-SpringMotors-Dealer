package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Repuestos;
import com.springmotors.springmotorsapp.entidades.Proveedor;
import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.repositorios.repuestosRepository;
import com.springmotors.springmotorsapp.repositorios.proveedorRepository;
import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/repuestos")
public class repuestosController {

    private final repuestosRepository repuestosRepository;
    private final proveedorRepository proveedorRepository;
    private final usuariosRepository usuariosRepository;

    public repuestosController(repuestosRepository repuestosRepository, 
                             proveedorRepository proveedorRepository,
                             usuariosRepository usuariosRepository) {
        this.repuestosRepository = repuestosRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping("/listar")
    public String listarRepuestos(Model model) {
        model.addAttribute("repuestos", repuestosRepository.findAll());
        return "repuestos/listarRepuestos";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        model.addAttribute("repuestos", repuestosRepository.findAll());
            // Solo agregamos esta línea para cargar los proveedores
    model.addAttribute("proveedores", proveedorRepository.findAll());
    
        return "repuestos/catalogoRepuestos";
    }

    @GetMapping("/form")
    public String mostrarFormulario(Model model, @RequestParam(required = false) Long id) {
        Repuestos repuesto = id == null ? new Repuestos() : repuestosRepository.findById(id).orElse(new Repuestos());
        
        // Obtener todos los proveedores
        List<Proveedor> proveedores = proveedorRepository.findAll();
        
        // Obtener todos los usuarios
        List<Usuarios> todosUsuarios = usuariosRepository.findAll();
        
        // Obtener IDs de usuarios asociados al repuesto
        Set<Long> usuariosAsociados = repuesto.getUsuarios().stream()
                .map(Usuarios::getId)
                .collect(Collectors.toSet());
        
        model.addAttribute("repuesto", repuesto);
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("usuarios", todosUsuarios);
        model.addAttribute("usuariosAsociados", usuariosAsociados);
        return "repuestos/formRepuestos";
    }

    @PostMapping("/guardar")
    public String guardarRepuesto(
            @ModelAttribute Repuestos repuesto,
            @RequestParam(value = "usuariosSeleccionados", required = false) List<Long> usuariosSeleccionados,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            @RequestParam("proveedorId") Long proveedorId) throws IOException {
        
        // Procesar imagen
        if (!imagenFile.isEmpty()) {
            repuesto.setFoto(imagenFile.getBytes());
            repuesto.setTipoImagen(imagenFile.getContentType());
        } else if (repuesto.getId() != null) {
            Repuestos repuestoExistente = repuestosRepository.findById(repuesto.getId()).orElse(null);
            if (repuestoExistente != null) {
                repuesto.setFoto(repuestoExistente.getFoto());
                repuesto.setTipoImagen(repuestoExistente.getTipoImagen());
            }
        }

        // Asignar proveedor
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
            .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        repuesto.setProveedor(proveedor);

        // Procesar usuarios seleccionados
        if (usuariosSeleccionados != null && !usuariosSeleccionados.isEmpty()) {
            Set<Usuarios> usuarios = usuariosSeleccionados.stream()
                    .map(id -> usuariosRepository.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            repuesto.setUsuarios(usuarios);
        } else {
            repuesto.setUsuarios(new HashSet<>());
        }

        repuestosRepository.save(repuesto);
        return "redirect:/repuestos/listar?success=" + (repuesto.getId() == null ? "create" : "update");
    }

    @GetMapping("/eliminar")
    public String eliminarRepuesto(@RequestParam Long id) {
        repuestosRepository.deleteById(id);
        return "redirect:/repuestos/listar";
    }

    @PostMapping("/comprar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> comprarRepuesto(
            @RequestParam Long id,
            @RequestBody Map<String, Object> requestData,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Long userId = Long.parseLong(requestData.get("userId").toString());
            Long sessionUserId = (Long) session.getAttribute("userId");
            if (sessionUserId == null || !sessionUserId.equals(userId)) {
                response.put("success", false);
                response.put("message", "Usuario no autenticado");
                return ResponseEntity.status(401).body(response);
            }

            Usuarios usuario = usuariosRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Repuestos repuesto = repuestosRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Repuesto no encontrado"));
            
            repuesto.getUsuarios().add(usuario);
            repuestosRepository.save(repuesto);
            
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