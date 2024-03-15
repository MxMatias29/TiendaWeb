package com.example.tienda.Model.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tienda.Model.DTO.Auth.AuthenticationRequest;
import com.example.tienda.Model.DTO.Auth.AuthenticationResponse;
import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Repository.UserRepository;
import com.example.tienda.Model.Service.Implementacion.JwtService;

import jakarta.validation.Valid;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    // public AuthenticationResponse login(@Valid AuthenticationRequest authRequest)
    // {

    // UsernamePasswordAuthenticationToken authToken = new
    // UsernamePasswordAuthenticationToken(authRequest.getUsername(),
    // authRequest.getPassword());

    // authenticationManager.authenticate(authToken);

    // UserEntity user =
    // userRepository.findByUsername(authRequest.getUsername()).get();
    // String jwt = jwtService.generateToken(user, generateExtraClaims(user));

    // return new AuthenticationResponse(jwt);
    // }

    public AuthenticationResponse login(@Valid AuthenticationRequest authRequest) {
        // Crear un token de autenticación con las credenciales proporcionadas
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());

        try {
            // Autenticar el token usando el authenticationManager
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            // Manejar la excepción si la autenticación falla (por ejemplo, credenciales
            // incorrectas)
            throw new RuntimeException("Credenciales inválidas", ex);
        }

        // Obtener el usuario de la base de datos utilizando el nombre de usuario
        // proporcionado
        UserEntity user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario está activo
        if (!user.getActividad()) {
            throw new RuntimeException("La cuenta de usuario está desactivada");
        }

        // Generar el token JWT
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        // Devolver el token JWT en una respuesta de autenticación
        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {

        Map<String, Object> extraclaims = new HashMap<>();
        extraclaims.put("name", user.getNombre());
        extraclaims.put("role", user.getRole().name());
        return null;
    }
}
