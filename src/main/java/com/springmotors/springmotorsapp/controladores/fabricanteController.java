package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Fabricante;
import com.springmotors.springmotorsapp.repositorios.fabricanteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fabricantes")
public class fabricanteController {

    private final fabricanteRepository fabricanteRepository;

    public fabricanteController(fabricanteRepository fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }

    @GetMapping("/listar")
    public String listarFabricantes(Model model) {
        model.addAttribute("fabricantes", fabricanteRepository.findAll());
        return "fabricantes/listarFabricantes";
    }

    // Mostrar formulario de creación
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("fabricante", new Fabricante());
        return "fabricantes/formFabricantes";
    }

    // Procesar creación de fabricante
    @PostMapping("/guardar")
    public String guardarFabricante(@ModelAttribute Fabricante fabricante, 
                                  RedirectAttributes redirectAttributes) {
        fabricanteRepository.save(fabricante);
        redirectAttributes.addFlashAttribute("exito", "Fabricante guardado correctamente");
        return "redirect:/fabricantes/listar?success=create";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Fabricante fabricante = fabricanteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de fabricante inválido: " + id));
        model.addAttribute("fabricante", fabricante);
        return "fabricantes/formFabricantes";
    }

    // Eliminar fabricante
    @GetMapping("/eliminar/{id}")
    public String eliminarFabricante(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            fabricanteRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("exito", "Fabricante eliminado correctamente");
            return "redirect:/fabricantes/listar?success=delete";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el fabricante");
            return "redirect:/fabricantes/listar";
        }
    }
}