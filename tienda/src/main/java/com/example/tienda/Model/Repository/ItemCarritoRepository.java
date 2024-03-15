package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.CarritoEntity;
import com.example.tienda.Model.Entity.ItemCarritoEntity;
import com.example.tienda.Model.Entity.ProductoEntity;

@Repository
public interface ItemCarritoRepository extends CrudRepository<ItemCarritoEntity, Long> {

    // Segun el carrito devuelve los productos
    List<ItemCarritoEntity> findByCarrito(CarritoEntity carrito);

    // Encuentra todos los elementos de carrito que contienen un producto específico.
    List<ItemCarritoEntity> findByProducto(ProductoEntity producto);

    @Query("SELECT i FROM ItemCarritoEntity i WHERE i.carrito = :carrito AND i.producto = :producto")
    List<ItemCarritoEntity> findByCarritoAndProducto(CarritoEntity carrito, ProductoEntity producto);

}
