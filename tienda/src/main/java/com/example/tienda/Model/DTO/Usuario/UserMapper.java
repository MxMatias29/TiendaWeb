package com.example.tienda.Model.DTO.Usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.tienda.Model.Entity.UserEntity;

@Mapper
public interface UserMapper {
    
    UserMapper mapper = Mappers.getMapper(UserMapper.class);
    
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellido", target = "apellido")
    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "correo", target = "correo")
    UserAdminDTO EntityToDto (UserEntity user);
}
