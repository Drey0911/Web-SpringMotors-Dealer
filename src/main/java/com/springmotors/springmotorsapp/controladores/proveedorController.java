package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Fabricante;
import com.springmotors.springmotorsapp.entidades.Proveedor;
import com.springmotors.springmotorsapp.repositorios.fabricanteRepository;
import com.springmotors.springmotorsapp.repositorios.proveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/proveedores")
public class proveedorController {

    @Autowired
    private proveedorRepository proveedorRepository;

    @Autowired
    private fabricanteRepository fabricanteRepository;

    @GetMapping("/listar")
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        model.addAttribute("proveedores", proveedores);
        return "proveedores/listarProveedores";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Proveedor proveedor = new Proveedor();
        List<Fabricante> todosFabricantes = fabricanteRepository.findAll();
        
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("todosFabricantes", todosFabricantes);
        return "proveedores/formProveedores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido: " + id));
        
        List<Fabricante> todosFabricantes = fabricanteRepository.findAll();
        
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("todosFabricantes", todosFabricantes);
        return "proveedores/formProveedores";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor, 
                                 @RequestParam(required = false) List<Long> fabricantesIds,
                                 RedirectAttributes redirectAttributes) {
        
        // Si es una actualización, mantenemos los fabricantes existentes si no se seleccionan nuevos
        if (proveedor.getId() != null && (fabricantesIds == null || fabricantesIds.isEmpty())) {
            Proveedor proveedorExistente = proveedorRepository.findById(proveedor.getId()).orElse(null);
            if (proveedorExistente != null) {
                proveedor.setFabricantes(proveedorExistente.getFabricantes());
            }
        } else if (fabricantesIds != null && !fabricantesIds.isEmpty()) {
            List<Fabricante> fabricantesSeleccionados = fabricanteRepository.findAllById(fabricantesIds);
            proveedor.setFabricantes(fabricantesSeleccionados);
            
            // Establecer la relación bidireccional
            for (Fabricante fabricante : fabricantesSeleccionados) {
                fabricante.setProveedor(proveedor);
            }
        }
        
        proveedorRepository.save(proveedor);
        
        String mensaje = proveedor.getId() == null ? "create" : "update";
        redirectAttributes.addAttribute("success", mensaje);
        return "redirect:/proveedores/listar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProveedor(@PathVariable Long id, 
                                    @ModelAttribute Proveedor proveedor, 
                                    @RequestParam(required = false) List<Long> fabricantesIds,
                                    RedirectAttributes redirectAttributes) {
        // Obtener el proveedor existente
        Proveedor proveedorExistente = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido: " + id));
        
        // Actualizar campos básicos
        proveedorExistente.setNombre(proveedor.getNombre());
        proveedorExistente.setEncargado(proveedor.getEncargado());
        proveedorExistente.setTelefono(proveedor.getTelefono());
        proveedorExistente.setEmail(proveedor.getEmail());
        proveedorExistente.setDireccion(proveedor.getDireccion());
        
        // Manejar fabricantes solo si se enviaron selecciones
        if (fabricantesIds != null) {
            // Desasociar los fabricantes actuales
            for (Fabricante fabricante : proveedorExistente.getFabricantes()) {
                fabricante.setProveedor(null);
                fabricanteRepository.save(fabricante);
            }
            
            // Asociar los nuevos fabricantes seleccionados
            if (!fabricantesIds.isEmpty()) {
                List<Fabricante> nuevosFabricantes = fabricanteRepository.findAllById(fabricantesIds);
                proveedorExistente.setFabricantes(nuevosFabricantes);
                
                for (Fabricante fabricante : nuevosFabricantes) {
                    fabricante.setProveedor(proveedorExistente);
                    fabricanteRepository.save(fabricante);
                }
            } else {
                proveedorExistente.setFabricantes(new ArrayList<>());
            }
        }
        
        proveedorRepository.save(proveedorExistente);
        redirectAttributes.addAttribute("success", "update");
        return "redirect:/proveedores/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido: " + id));
        
        // Desasociar los fabricantes antes de eliminar el proveedor
        for (Fabricante fabricante : proveedor.getFabricantes()) {
            fabricante.setProveedor(null);
            fabricanteRepository.save(fabricante);
        }
        
        // Limpiar la relación antes de eliminar
        proveedor.setFabricantes(new ArrayList<>());
        proveedorRepository.save(proveedor);
        
        // Ahora podemos eliminar el proveedor
        proveedorRepository.delete(proveedor);
        
        redirectAttributes.addAttribute("success", "delete");
        return "redirect:/proveedores/listar";
    }
}