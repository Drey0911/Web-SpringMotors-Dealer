package com.springmotors.springmotorsapp.handlers;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private usuariosRepository usuariosRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        // Obtener el usuario autenticado
        String username = authentication.getName();
        Usuarios usuario = usuariosRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Guardar el ID del usuario en la sesión
        request.getSession().setAttribute("userId", usuario.getId());
        
        // Redirigir a la página principal
        setDefaultTargetUrl("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}