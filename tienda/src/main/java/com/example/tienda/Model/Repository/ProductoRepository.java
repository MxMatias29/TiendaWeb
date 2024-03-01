package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.ProductoEntity;

@Repository
public interface ProductoRepository extends CrudRepository<ProductoEntity, Integer> {
    @Query(value = "SELECT * FROM productos c WHERE c.actividad = true", nativeQuery = true)
    List<ProductoEntity> findAllActive();

    @Query(value = "SELECT * FROM productos c WHERE c.actividad = false", nativeQuery = true)
    List<ProductoEntity> findAllFalse();

    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);

    List<ProductoEntity> findByMarcaNombreContainingIgnoreCase(String marca);

    List<ProductoEntity> findByCategoriaNombreContainingIgnoreCase(String categoria);

}
