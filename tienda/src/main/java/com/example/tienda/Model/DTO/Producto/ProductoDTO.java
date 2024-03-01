package com.example.tienda.Model.DTO.Producto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private float precio;
    private Integer stock;
}
