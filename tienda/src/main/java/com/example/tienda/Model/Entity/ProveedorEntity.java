package com.example.tienda.Model.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "proveedor")
public class ProveedorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id_proveedor")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_proveedor;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "ruc", unique = true)
    @NotEmpty
    @Size(min = 11, max = 11)
    private String ruc;

    @Column(name = "nombre")
    @NotEmpty
    @Size(max = 100)
    private String nombre;

    @Column(name = "direccion")
    @NotEmpty
    @Size(max = 100)
    private String direccion;

    @Column(name = "telefono")
    @NotEmpty
    @Pattern(regexp = "(\\+51)[0-9]{9}")
    private String telefono;

    @Column(name = "correo")
    @NotEmpty
    @Email
    private String correo;

    @Column(name = "actividad")
    private Boolean actividad;
}
