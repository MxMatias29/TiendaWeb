package com.example.tienda.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.DTO.Auth.AuthenticationRequest;
import com.example.tienda.Model.DTO.Auth.AuthenticationResponse;
import com.example.tienda.Model.DTO.Usuario.UserAdminDTO;
import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Service.AuthenticationService;
import com.example.tienda.Model.Service.IUsuario;
import com.example.tienda.Payload.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private IUsuario usuarioService;

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest) {

        AuthenticationResponse jwtDto = authenticationService.login(authRequest);

        return ResponseEntity.ok(jwtDto);
    }

    @PreAuthorize("hasAuthority('READ_ALL_USER_DTO')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllProveedorPartial() {
        try {
            List<UserAdminDTO> users = usuarioService.findAll();

            if (users.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontraron Usuarios")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Usuarios")
                        .object(users)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('READ_ALL_USER_ACTIVE_ADMIN')")
    @GetMapping("/user/activo")
    public ResponseEntity<?> getAllProveedorPartialActive() {
        try {
            List<UserAdminDTO> users = usuarioService.findAllActive();

            if (users.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontraron Usuarios")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Usuarios Activos")
                        .object(users)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('READ_ALL_USER_INACTIVE_ADMIN')")
    @GetMapping("/user/inactivo")
    public ResponseEntity<?> getAllProveedorPartialInactive() {
        try {
            List<UserAdminDTO> users = usuarioService.findAllInactive();

            if (users.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontraron Usuarios")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Usuarios Inactivos")
                        .object(users)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('MY_PROFILE_SHOW')")
    @GetMapping("/show-profile")
    public ResponseEntity<?> getFindById() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<UserEntity> userId = usuarioService.findByUsername(username);
            if (userId == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID del usuario")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Usuario Encontrado")
                        .object(userId)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('EDIT_PASSWORD')")
    @PostMapping("/edit-password")
    public ResponseEntity<String> cambiarContrasena(@RequestParam String nuevaContrasena,
            Authentication authentication) {

        String username = authentication.getName();

        // Buscar al usuario por su nombre de usuario
        UserEntity user = usuarioService.findByUsername(username).orElse(null);

        if (user != null) {
            // Realizar el cambio de contraseña
            usuarioService.cambiarPassword(user.getId(), nuevaContrasena);
            return ResponseEntity.ok("Contraseña cambiada exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo encontrar al usuario autenticado.");
        }
    }

    @PreAuthorize("hasAuthority('SEARCH_USUARIO')")
    @GetMapping("/user/buscar/{search}")
    public ResponseEntity<?> buscarUsuario(@PathVariable String search) {
        try {
            List<UserEntity> lista = usuarioService.searchUser(search);

            if (lista.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado de la busqueda")
                        .object(lista)
                        .build(), HttpStatus.OK);
            }

        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("permitAll")
    @PostMapping("/create-account")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserEntity user) {
        UserEntity saveEntity = null;
        try {
            saveEntity = usuarioService.save(user);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado Exitosamente")
                    .object(saveEntity)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(e)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('DISABLE_ACCOUNT')")
    @PatchMapping("/disable-account/{id}")
    public ResponseEntity<?> changeOfStatus(@PathVariable Long id) {

        try {
            UserEntity userId = usuarioService.findById(id);
            if (userId != null) {
                UserEntity user = usuarioService.changeOfActivity(userId);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado Actualizado")
                        .object(user)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el Usuario")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(e)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_ACCOUNT')")
    @DeleteMapping("/delete-account/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            UserEntity userId = usuarioService.findById(id);
            if (userId != null) {
                usuarioService.delete(userId);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Usuario Eliminado")
                        .object(null)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el usuario")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(e)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

}
