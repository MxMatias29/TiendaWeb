package com.example.tienda.Model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.tienda.util.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "usuario")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 11)
    private String codigo;

    @NotEmpty
    @Size(max = 30, min = 5, message = "UserName: 5 - 30 caracteres")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "nombre")
    @NotEmpty
    @Size(max = 50, min = 3, message = "Nombre: 3 - 50 caracteres")
    private String nombre;

    @Column(name = "apellido")
    @NotEmpty
    @Size(max = 50, min = 3, message = "Apellido: 3 - 50 caracteres")
    private String apellido;

    private String password;

    @Column(name = "dni", unique = true)
    @NotEmpty
    @Size(min = 8, max = 8, message = "Dni: 8 Digitos")
    private String dni;

    @Column(name = "telefono")
    @Size(max = 9, min = 9, message = "Numero: 9 digitos")
    @NotEmpty
    private String telefono;

    @Email
    @NotBlank
    @Size(max = 255, min = 5, message = "Correo: 5 - 255 Caracteres")
    @Column(name = "correo", unique = true)
    private String correo;

    private Boolean actividad = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CarritoEntity> carritos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = role.getPermisos().stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
