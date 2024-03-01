package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.Entity.MarcaEntity;

public interface IMarca {
    List<MarcaEntity> findAll();

    List<MarcaEntity> findAllActive();

    List<MarcaEntity> findAllFalse();

    MarcaEntity findById(Integer id);

    MarcaEntity save(MarcaEntity entity);

    MarcaEntity changeofStatus(MarcaEntity entity);

    void delete(MarcaEntity entity);
}
