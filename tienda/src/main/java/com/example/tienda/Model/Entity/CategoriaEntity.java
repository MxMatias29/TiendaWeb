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
@Table(name = "categoria")
public class CategoriaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_category;

    @Column(name = "nombre")
    @NotEmpty
    @Size(max = 50, min = 3, message = " Nombre: 3 - 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "No se permite números, caracteres especiales")
    private String nombre;

    @Column(name = "actividad")
    private Boolean actividad = true;

}
