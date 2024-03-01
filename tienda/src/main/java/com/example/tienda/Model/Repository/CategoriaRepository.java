package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.CategoriaEntity;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriaEntity, Integer> {
    @Query(value = "SELECT * FROM categoria c WHERE c.actividad = true", nativeQuery = true)
    List<CategoriaEntity> findAllActive();

    @Query(value = "SELECT * FROM categoria c WHERE c.actividad = false", nativeQuery = true)
    List<CategoriaEntity> findAllFalse();

}
