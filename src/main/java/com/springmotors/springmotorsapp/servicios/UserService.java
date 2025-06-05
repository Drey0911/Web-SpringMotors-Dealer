package com.springmotors.springmotorsapp.servicios;

import com.springmotors.springmotorsapp.entidades.Usuarios;
import com.springmotors.springmotorsapp.repositorios.usuariosRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final usuariosRepository usuariosRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(usuariosRepository usuariosRepo, PasswordEncoder passwordEncoder) {
        this.usuariosRepo = usuariosRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepo.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .roles(usuario.getRol())
                .build();
    }

    public void registrarUsuario(Usuarios usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol("USER"); // Rol por defecto
        usuariosRepo.save(usuario);
    }
}