package com.example.tienda.Model.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Size(max = 50, min = 3, message = " Nombre: 3 - 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "No se permite números, caracteres especiales")
    @NotEmpty
    private String nombre;

    @Size(max = 50, min = 3, message = " Apellido: 3 - 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "No se permite números, caracteres especiales")
    @NotEmpty
    private String apellido;

    @NotNull
    @Size(max = 8, min = 8, message = "Dni: 8 digitos")
    @Column(unique = true, nullable = false)
    private Integer dni;

    @Size(max = 9, min = 9, message = "Numero: 9 digitos")
    @NotEmpty
    @Pattern(regexp = "(\\d{9}$", message = "Solo números")
    private String telefono;

    @Email
    @Column(unique = true, nullable = false)
    private String correo;

    private String password;

    private Boolean actividad;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolEntity> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private CarritoEntity cart;

}
