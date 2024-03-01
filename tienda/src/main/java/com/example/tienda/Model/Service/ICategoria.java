package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.Entity.CategoriaEntity;

public interface ICategoria {
    List<CategoriaEntity> findAll();

    List<CategoriaEntity> findAllActive();

    List<CategoriaEntity> findAllFalse();

    CategoriaEntity findById(Integer id_category);

    CategoriaEntity save(CategoriaEntity categoryEntity);

    CategoriaEntity changeofStatus(CategoriaEntity categoryEntity);

    void delete(CategoriaEntity categoryEntity);
}
