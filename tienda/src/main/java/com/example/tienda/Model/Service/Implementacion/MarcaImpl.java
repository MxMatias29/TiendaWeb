package com.example.tienda.Model.Service.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.Entity.MarcaEntity;
import com.example.tienda.Model.Repository.MarcaRepository;
import com.example.tienda.Model.Service.IMarca;

@Service
public class MarcaImpl implements IMarca {

    @Autowired
    private MarcaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<MarcaEntity> findAll() {
        return (List<MarcaEntity>) repository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<MarcaEntity> findAllActive() {
        return repository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<MarcaEntity> findAllFalse() {
        return repository.findAllFalse();
    }
    
    @Transactional(readOnly = true)
    @Override
    public MarcaEntity findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
    
    @Transactional
    @Override
    public MarcaEntity save(MarcaEntity entity) {
        return repository.save(entity);
    }
    
    @Transactional
    @Override
    public MarcaEntity changeofStatus(MarcaEntity entity) {
        MarcaEntity obj = repository.findById(entity.getId_marca()).orElse(null);
        if (obj.getActividad()) {
            obj.setActividad(false);
        } else {
            obj.setActividad(true);
        }
        
        return repository.save(obj);
    }
    
    @Transactional
    @Override
    public void delete(MarcaEntity entity) {
        repository.delete(entity);
    }

}
