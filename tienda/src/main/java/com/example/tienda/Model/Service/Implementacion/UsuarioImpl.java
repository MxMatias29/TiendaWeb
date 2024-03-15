package com.example.tienda.Model.Service.Implementacion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.DTO.Usuario.UserAdminDTO;
import com.example.tienda.Model.DTO.Usuario.UserMapper;
import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Repository.UserRepository;
import com.example.tienda.Model.Service.IUsuario;
import com.example.tienda.util.Role;

@Service
public class UsuarioImpl implements IUsuario {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<UserAdminDTO> findAll() {
        List<UserEntity> usuarios = (List<UserEntity>) repository.findAll();

        List<UserAdminDTO> usuarioAdmin = usuarios.stream().map(
                user -> UserMapper.mapper.EntityToDto(user))
                .collect(Collectors.toList());

        return usuarioAdmin;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserAdminDTO> findAllActive() {
        List<UserEntity> usuarios = (List<UserEntity>) repository.findAllActive();

        List<UserAdminDTO> usuarioAdmin = usuarios.stream().map(
                user -> UserMapper.mapper.EntityToDto(user))
                .collect(Collectors.toList());

        return usuarioAdmin;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserAdminDTO> findAllInactive() {
        List<UserEntity> usuarios = (List<UserEntity>) repository.findAllInactive();

        List<UserAdminDTO> usuarioAdmin = usuarios.stream().map(
                user -> UserMapper.mapper.EntityToDto(user))
                .collect(Collectors.toList());

        return usuarioAdmin;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity userdto) {
        userdto.setRole(Role.CUSTOMER);
        userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
        return repository.save(userdto);
    }

    @Transactional
    @Override
    public void delete(UserEntity user) {
        repository.delete(user);
    }

    @Transactional
    @Override
    public UserEntity changeOfActivity(UserEntity user) {
        UserEntity obj = repository.findById(user.getId()).orElse(null);

        if (obj.getActividad()) {
            obj.setActividad(false);
        } else {
            obj.setActividad(true);
        }

        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> searchUser(String search) {
        return repository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrCorreoContainingIgnoreCaseOrDniContainingIgnoreCase(
                        search, search, search, search);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Transactional
    @Override
    public void cambiarPassword(Long userId, String nuevoPassword) {
        UserEntity user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Codificar la nueva contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(nuevoPassword));

        repository.save(user);

    }

}
