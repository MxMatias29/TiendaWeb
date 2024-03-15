package com.example.tienda.Model.DTO.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminDTO {
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;
    private String telefono;
}
