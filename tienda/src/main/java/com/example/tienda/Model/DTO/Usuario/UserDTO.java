package com.example.tienda.Model.DTO.Usuario;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String dni;
    @NotBlank
    private String telefono;
    @NotBlank
    private String correo;
    @NotBlank
    private String password;

    private Set<String> roles;
}
