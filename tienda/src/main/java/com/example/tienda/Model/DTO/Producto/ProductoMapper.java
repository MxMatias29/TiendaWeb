package com.example.tienda.Model.DTO.Producto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.tienda.Model.Entity.ProductoEntity;

@Mapper
public interface ProductoMapper {
    ProductoMapper mapper = Mappers.getMapper(ProductoMapper.class);
  
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "precio", target = "precio")
    @Mapping(source = "stock", target = "stock")
    ProductoDTO EntityToDto(ProductoEntity producto); 

    ProductClienteDTO EntityToDtoCliente(ProductoEntity producto);
}
