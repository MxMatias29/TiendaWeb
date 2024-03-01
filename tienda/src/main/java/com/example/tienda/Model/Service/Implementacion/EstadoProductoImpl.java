package com.example.tienda.Model.Service.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.Entity.EstadoProductoEntity;
import com.example.tienda.Model.Repository.EstadoProductoRepository;
import com.example.tienda.Model.Service.IEstadoProducto;

@Service
public class EstadoProductoImpl implements IEstadoProducto {

    @Autowired
    private EstadoProductoRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<EstadoProductoEntity> findAll() {
        return (List<EstadoProductoEntity>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EstadoProductoEntity> findAllActive() {
        return repository.findAllActive();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EstadoProductoEntity> findAllFalse() {
        return repository.findAllFalse();
    }

    @Transactional(readOnly = true)
    @Override
    public EstadoProductoEntity findById(Integer id_estado) {
        return repository.findById(id_estado).orElse(null);
    }
    
    @Transactional
    @Override
    public EstadoProductoEntity save(EstadoProductoEntity estadoEntity) {
        return repository.save(estadoEntity);
    }
    
    @Transactional
    @Override
    public EstadoProductoEntity changeofStatus(EstadoProductoEntity estadoEntity) {
        EstadoProductoEntity objEstado = repository.findById(estadoEntity.getId_estado()).orElse(null);
        if (objEstado.getActividad()) {
            objEstado.setActividad(false);
        } else {
            objEstado.setActividad(true);
        }
        
        return repository.save(objEstado);
    }
    

    @Transactional
    @Override
    public void delete(EstadoProductoEntity entity) {
        repository.delete(entity);
    }

}
