package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.Entity.EstadoProductoEntity;

public interface IEstadoProducto {

    List<EstadoProductoEntity> findAll();

    List<EstadoProductoEntity> findAllActive();

    List<EstadoProductoEntity> findAllFalse();

    EstadoProductoEntity findById(Integer id_estado);

    EstadoProductoEntity save(EstadoProductoEntity estadoEntity);

    EstadoProductoEntity changeofStatus(EstadoProductoEntity estadoEntity);

    void delete(EstadoProductoEntity entity);
}
