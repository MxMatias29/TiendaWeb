package com.example.tienda.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT * FROM usuario p WHERE p.actividad = true", nativeQuery = true)
    List<UserEntity> findAllActive();

    @Query(value = "SELECT * FROM usuario p WHERE p.actividad = false", nativeQuery = true)
    List<UserEntity> findAllInactive();

    List<UserEntity> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrCorreoContainingIgnoreCaseOrDniContainingIgnoreCase(
            String nombre, String apellido, String correo, String dni);

}
