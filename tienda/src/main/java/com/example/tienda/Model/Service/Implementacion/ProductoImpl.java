package com.example.tienda.Model.Service.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.DTO.Producto.ProductClienteDTO;
import com.example.tienda.Model.DTO.Producto.ProductoDTO;
import com.example.tienda.Model.DTO.Producto.ProductoMapper;
import com.example.tienda.Model.Entity.ProductoEntity;
import com.example.tienda.Model.Repository.ProductoRepository;
import com.example.tienda.Model.Service.IProducto;

@Service
public class ProductoImpl implements IProducto {

   
    @Autowired
    private ProductoRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> findAll() {
        List<ProductoEntity> products = (List<ProductoEntity>) repository.findAll();

        List<ProductoDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDto(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> findAllActive() {
        List<ProductoEntity> products = (List<ProductoEntity>) repository.findAllActive();

        List<ProductoDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDto(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> findAllInactive() {
        List<ProductoEntity> products = (List<ProductoEntity>) repository.findAllFalse();

        List<ProductoDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDto(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public ProductoEntity findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public ProductoEntity save(ProductoEntity producto) {
        producto.setActividad(true);
        return repository.save(producto);
    }

    @Transactional
    @Override
    public void delete(ProductoEntity producto) {
        repository.delete(producto);
    }

    @Transactional
    @Override
    public ProductoEntity changeofState(ProductoEntity producto) {
        ProductoEntity obj = repository.findById(producto.getId_productos()).orElse(null);
        if (obj.getActividad()) {
            obj.setActividad(false);
        } else {
            obj.setActividad(true);
        }

        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        List<ProductoEntity> products = (List<ProductoEntity>) repository.findByNombreContainingIgnoreCase(nombre);

        List<ProductoDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDto(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductClienteDTO> buscarPorNombreMarca(String marca) {
        List<ProductoEntity> products = (List<ProductoEntity>) repository.findByMarcaNombreContainingIgnoreCase(marca);

        List<ProductClienteDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDtoCliente(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductClienteDTO> buscarPorNombreCategoria(String categoria) {
        List<ProductoEntity> products = (List<ProductoEntity>) repository
                .findByCategoriaNombreContainingIgnoreCase(categoria);

        List<ProductClienteDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDtoCliente(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductClienteDTO> findAllCliente() {

        List<ProductoEntity> products = (List<ProductoEntity>) repository.findAll();

        List<ProductClienteDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDtoCliente(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Override
    public List<ProductClienteDTO> barraBusqueda(String search) {
        List<ProductoEntity> products = (List<ProductoEntity>) repository
                .findByNombreContainingIgnoreCaseOrMarcaNombreContainingIgnoreCaseOrCategoriaNombreContainingIgnoreCase(
                        search, search, search);

        List<ProductClienteDTO> productDTOs = products.stream().map(
                producto -> ProductoMapper.mapper.EntityToDtoCliente(producto))
                .collect(Collectors.toList());

        return productDTOs;
    }


}
