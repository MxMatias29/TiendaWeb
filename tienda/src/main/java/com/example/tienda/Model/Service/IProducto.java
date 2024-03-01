package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.DTO.Producto.ProductoDTO;
import com.example.tienda.Model.Entity.ProductoEntity;

public interface IProducto {
    List<ProductoDTO> findAll();

    List<ProductoDTO> findAllActive();

    List<ProductoDTO> findAllInactive();

    ProductoEntity findById(Integer id);

    ProductoEntity save(ProductoEntity producto);

    void delete(ProductoEntity producto);

    ProductoEntity changeofState(ProductoEntity producto);

    List<ProductoEntity> buscarPorNombre(String nombre);

    List<ProductoEntity> buscarPorNombreMarca(String marca);

    List<ProductoEntity> buscarPorNombreCategoria(String categoria);
}



