package com.example.tienda.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.MarcaEntity;

@Repository
public interface MarcaRepository extends CrudRepository<MarcaEntity, Integer> {
    @Query(value = "SELECT * FROM marca c WHERE c.actividad = true", nativeQuery = true)
    List<MarcaEntity> findAllActive();

    @Query(value = "SELECT * FROM marca c WHERE c.actividad = false", nativeQuery = true)
    List<MarcaEntity> findAllFalse();

}
