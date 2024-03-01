package com.example.tienda.Model.Service.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ProductoEntity> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoEntity> buscarPorNombreMarca(String marca) {
        return repository.findByMarcaNombreContainingIgnoreCase(marca);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductoEntity> buscarPorNombreCategoria(String categoria) {
        return repository.findByCategoriaNombreContainingIgnoreCase(categoria);

    }

}
