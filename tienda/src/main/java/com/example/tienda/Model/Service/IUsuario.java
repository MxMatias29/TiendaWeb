package com.example.tienda.Model.Service;

import java.util.List;
// import java.util.Optional;
import java.util.Optional;

import com.example.tienda.Model.DTO.Usuario.UserAdminDTO;
import com.example.tienda.Model.Entity.UserEntity;

public interface IUsuario {
    List<UserAdminDTO> findAll();

    List<UserAdminDTO> findAllActive();

    List<UserAdminDTO> findAllInactive();

    UserEntity findById(Long id);

    UserEntity save(UserEntity userdto);

    void delete(UserEntity user);

    UserEntity changeOfActivity(UserEntity user);

    List<UserEntity> searchUser (String search);

    Optional<UserEntity> findByUsername(String username);

    void cambiarPassword(Long userId, String nuevoPassword);

}
