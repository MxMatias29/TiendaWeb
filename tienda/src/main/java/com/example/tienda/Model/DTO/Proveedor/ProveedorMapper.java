package com.example.tienda.Model.DTO.Proveedor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.tienda.Model.Entity.ProveedorEntity;

@Mapper
public interface ProveedorMapper {
    ProveedorMapper mapper = Mappers.getMapper(ProveedorMapper.class);

    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "ruc", target = "ruc")
    @Mapping(source = "telefono", target = "telefono")
    ProveedorDTO EntityToDto(ProveedorEntity proveedor);
}
