package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.EstadoProductoEntity;

@Repository
public interface EstadoProductoRepository extends CrudRepository<EstadoProductoEntity, Integer> {
    @Query(value = "SELECT * FROM estado_producto c WHERE c.actividad = true", nativeQuery = true)
    List<EstadoProductoEntity> findAllActive();

    @Query(value = "SELECT * FROM estado_producto c WHERE c.actividad = false", nativeQuery = true)
    List<EstadoProductoEntity> findAllFalse();

}
