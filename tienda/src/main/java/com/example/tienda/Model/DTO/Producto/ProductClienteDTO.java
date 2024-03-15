package com.example.tienda.Model.DTO.Producto;

import com.example.tienda.Model.Entity.CategoriaEntity;
import com.example.tienda.Model.Entity.EstadoProductoEntity;
import com.example.tienda.Model.Entity.MarcaEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductClienteDTO {
    private String codigo;
    private String nombre;
    private float precio;
    private String descripcion;
    private String foto;
    private MarcaEntity marca;
    private CategoriaEntity categoria;
    private EstadoProductoEntity estado;
    private String codigobarra;
    private Integer stock;    
}
