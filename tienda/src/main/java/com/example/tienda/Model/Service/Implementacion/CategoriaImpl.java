package com.example.tienda.Model.Service.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.Entity.CategoriaEntity;
import com.example.tienda.Model.Repository.CategoriaRepository;
import com.example.tienda.Model.Service.ICategoria;

@Service
public class CategoriaImpl implements ICategoria {

    @Autowired
    private CategoriaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaEntity> findAll() {
        return (List<CategoriaEntity>) repository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CategoriaEntity> findAllActive() {
        return (List<CategoriaEntity>) repository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<CategoriaEntity> findAllFalse() {
        return (List<CategoriaEntity>) repository.findAllFalse();
    }
    
    @Transactional(readOnly = true)
    @Override
    public CategoriaEntity findById(Integer id_category) {
        return repository.findById(id_category).orElse(null);
    }
    
    @Transactional
    @Override
    public CategoriaEntity save(CategoriaEntity categoryEntity) {
        return repository.save(categoryEntity);
    }
    
    @Transactional
    @Override
    public CategoriaEntity changeofStatus(CategoriaEntity categoryEntity) {
        CategoriaEntity obj = repository.findById(categoryEntity.getId_category()).orElse(null);
        if (obj.getActividad()) {
            obj.setActividad(false);
        } else {
            obj.setActividad(true);
        }
        
        return repository.save(obj);
        
    }
    
    @Transactional
    @Override
    public void delete(CategoriaEntity categoryEntity) {
        repository.delete(categoryEntity);
    }
}
