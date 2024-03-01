package com.example.tienda.Model.Service.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.DTO.Proveedor.ProveedorDTO;
import com.example.tienda.Model.DTO.Proveedor.ProveedorMapper;
import com.example.tienda.Model.Entity.ProveedorEntity;
import com.example.tienda.Model.Repository.ProveedorRepository;
import com.example.tienda.Model.Service.IProveedor;

@Service
public class ProveedorImpl implements IProveedor {

    @Autowired
    private ProveedorRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<ProveedorDTO> findAll() {
        List<ProveedorEntity> proveedores = (List<ProveedorEntity>) repository.findAll();

        List<ProveedorDTO> proveedorDTOs = proveedores.stream().map(
                proveedor -> ProveedorMapper.mapper.EntityToDto(proveedor))
                .collect(Collectors.toList());

        return proveedorDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProveedorDTO> findAllActive() {
        List<ProveedorEntity> proveedores = (List<ProveedorEntity>) repository.findAllActive();

        List<ProveedorDTO> proveedorDTOs = proveedores.stream().map(
                proveedor -> ProveedorMapper.mapper.EntityToDto(proveedor))
                .collect(Collectors.toList());

        return proveedorDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProveedorDTO> findAllInactive() {
        List<ProveedorEntity> proveedores = (List<ProveedorEntity>) repository.findAllInactive();

        List<ProveedorDTO> proveedorDTOs = proveedores.stream().map(
                proveedor -> ProveedorMapper.mapper.EntityToDto(proveedor))
                .collect(Collectors.toList());

        return proveedorDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public ProveedorEntity findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public ProveedorEntity save(ProveedorEntity proveedor) {
        return repository.save(proveedor);
    }

    @Transactional
    @Override
    public void delete(ProveedorEntity proveedor) {
        repository.delete(proveedor);
    }

    @Transactional
    @Override
    public ProveedorEntity changeofState(ProveedorEntity proveedor) {
        ProveedorEntity obj = repository.findById(proveedor.getId_proveedor()).orElse(null);
        if (obj.getActividad()) {
            obj.setActividad(false);
        } else {
            obj.setActividad(true);
        }

        return repository.save(obj);
    }

    @Transactional
    @Override
    public List<ProveedorEntity> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }
}
