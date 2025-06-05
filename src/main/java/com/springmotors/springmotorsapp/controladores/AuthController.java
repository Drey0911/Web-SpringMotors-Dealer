package com.springmotors.springmotorsapp.controladores;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.servicios.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registroExitoso", required = false) String registroExitoso,
            Authentication authentication,
            Model model) {
        
        // Si el usuario ya está autenticado, redirigir a la página principal
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        
        // Agregar mensajes según los parámetros
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión correctamente");
        }
        if (registroExitoso != null) {
            model.addAttribute("registroExitoso", "Registro exitoso. Por favor inicia sesión.");
        }
        
        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model, Authentication authentication) {
        // Si el usuario ya está autenticado, redirigir
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        
        model.addAttribute("usuario", new Usuarios());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuarios usuario, Model model) {
        try {
            userService.registrarUsuario(usuario);
            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            return "auth/registro";
        }
    }
}