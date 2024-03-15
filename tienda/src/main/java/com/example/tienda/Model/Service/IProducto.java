package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.DTO.Producto.ProductClienteDTO;
import com.example.tienda.Model.DTO.Producto.ProductoDTO;
import com.example.tienda.Model.Entity.ProductoEntity;

public interface IProducto {
    List<ProductoDTO> findAll();

    List<ProductClienteDTO> findAllCliente();

    List<ProductoDTO> findAllActive();

    List<ProductoDTO> findAllInactive();

    ProductoEntity findById(Integer id);

    ProductoEntity save(ProductoEntity producto);

    void delete(ProductoEntity producto);

    ProductoEntity changeofState(ProductoEntity producto);

    // ADMIN

    List<ProductoDTO> buscarPorNombre(String nombre);

    // Filtro - Barra Busqueda -> CLIENTE

    List<ProductClienteDTO> buscarPorNombreMarca(String marca);

    List<ProductClienteDTO> buscarPorNombreCategoria(String categoria);

    List<ProductClienteDTO> barraBusqueda(String search);
}
