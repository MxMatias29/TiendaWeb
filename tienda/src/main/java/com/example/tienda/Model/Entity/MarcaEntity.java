package com.example.tienda.Model.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "marca")
public class MarcaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_marca")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_marca;

    @Column(name = "nombre")
    @NotEmpty
    @Size(max = 50, min = 3, message = " Nombre: 3 - 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "No se permite números, caracteres especiales")
    private String nombre;

    @Column(name = "actividad")
    private Boolean actividad = true;
}
