package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.example.tienda.Model.Entity.ProveedorEntity;

@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Integer> {

    @Query(value = "SELECT * FROM proveedor p WHERE p.actividad = true", nativeQuery = true)
    List<ProveedorEntity> findAllActive();

    @Query(value = "SELECT * FROM proveedor p WHERE p.actividad = false", nativeQuery = true)
    List<ProveedorEntity> findAllInactive();

    List<ProveedorEntity> findByNombreContainingIgnoreCase(String nombre);
}
