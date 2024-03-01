package com.example.tienda.Model.Service;

import java.util.List;

import com.example.tienda.Model.DTO.Proveedor.ProveedorDTO;
import com.example.tienda.Model.Entity.ProveedorEntity;

public interface IProveedor {
    List<ProveedorDTO> findAll();

    List<ProveedorDTO> findAllActive();

    List<ProveedorDTO> findAllInactive();

    ProveedorEntity findById(Integer id);

    ProveedorEntity save(ProveedorEntity proveedor);

    void delete(ProveedorEntity proveedor);

    ProveedorEntity changeofState(ProveedorEntity proveedor);

    List<ProveedorEntity> buscarPorNombre(String nombre);
}
