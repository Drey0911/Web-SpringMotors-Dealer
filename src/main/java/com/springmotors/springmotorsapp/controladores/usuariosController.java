package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class usuariosController {

    private final usuariosRepository usuariosRepo;
    private final PasswordEncoder passwordEncoder;

    public usuariosController(usuariosRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Mostrar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuarios> usuarios = usuariosRepo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listarUsuarios";
    }

    // Mostrar formulario para crear nuevo usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "usuarios/formUsuarios";
    }

    // Procesar creación de nuevo usuario
    @PostMapping("/nuevo")
    public String crearUsuario(@ModelAttribute Usuarios usuario) {
        // Cifrar la contraseña antes de guardar
        String contrasenaCifrada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaCifrada);
        
        usuariosRepo.save(usuario);
        return "redirect:/usuarios?success=create";
    }

    // Mostrar formulario para editar usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable Long id, Model model) {
        Usuarios usuario = usuariosRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));
        
        // Creamos un objeto temporal sin contraseña para el formulario
        Usuarios usuarioForm = new Usuarios();
        usuarioForm.setId(usuario.getId());
        usuarioForm.setNombreUsuario(usuario.getNombreUsuario());
        usuarioForm.setRol(usuario.getRol());
        
        model.addAttribute("usuario", usuarioForm);
        return "usuarios/formUsuarios";
    }

    // Procesar actualización de usuario
    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuarios usuarioForm) {
        Usuarios usuarioExistente = usuariosRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));
        
        // Actualizar solo los campos permitidos
        usuarioExistente.setNombreUsuario(usuarioForm.getNombreUsuario());
        usuarioExistente.setRol(usuarioForm.getRol());
        
        // Solo actualizar contraseña si se proporcionó una nueva
        if (usuarioForm.getContrasena() != null && !usuarioForm.getContrasena().isEmpty()) {
            String contrasenaCifrada = passwordEncoder.encode(usuarioForm.getContrasena());
            usuarioExistente.setContrasena(contrasenaCifrada);
        }
        
        usuariosRepo.save(usuarioExistente);
        return "redirect:/usuarios?success=update";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        Usuarios usuario = usuariosRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));
        usuariosRepo.delete(usuario);
        return "redirect:/usuarios?success=delete";
    }
}