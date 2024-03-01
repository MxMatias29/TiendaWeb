package com.example.tienda.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.Entity.MarcaEntity;
import com.example.tienda.Model.Service.IMarca;
import com.example.tienda.Payload.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MarcaController {

    @Autowired
    private IMarca service;

    @GetMapping("/marca")
    public ResponseEntity<?> findAll() {
        try {
            List<MarcaEntity> listAll = service.findAll();
            if (listAll.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Marcas")
                        .object(listAll)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/marca/activo")
    public ResponseEntity<?> findAllActive() {
        try {
            List<MarcaEntity> listAllActives = service.findAllActive();
            if (listAllActives.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Marcas Activas")
                        .object(listAllActives)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/marca/inactivo")
    public ResponseEntity<?> findAllFalse() {
        try {
            List<MarcaEntity> listAllActives = service.findAllFalse();
            if (listAllActives.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Marcas Inactivas")
                        .object(listAllActives)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            MarcaEntity marca = service.findById(id);
            if (marca == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado Encontrad")
                        .object(marca)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/marca")
    public ResponseEntity<?> saveMarcas(@Valid @RequestBody MarcaEntity marcasEntity) {
        MarcaEntity marcas = null;
        try {
            marcas = service.save(marcasEntity);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Marca Guardada")
                    .object(marcas)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/marca/{id}")
    public ResponseEntity<?> updateMarcas(@Valid @PathVariable Integer id,
            @RequestBody MarcaEntity marcasEntity) {
        MarcaEntity marcasUpdate = null;
        try {
            MarcaEntity categoryById = service.findById(id);
            if (categoryById != null) {
                marcasUpdate = service.save(marcasEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Registro actualizado")
                        .object(marcasUpdate)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el Id")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/marca/{id}")
    public ResponseEntity<?> changeOfState(@PathVariable Integer id) {
        try {
            MarcaEntity marcasId = service.findById(id);
            if (marcasId != null) {
                MarcaEntity marcaState = service.changeofStatus(marcasId);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado actualizado")
                        .object(marcaState)
                        .build(), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Id no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<?> deleteMarcas(@PathVariable Integer id) {
        try {
            MarcaEntity marcaById = service.findById(id);
            if (marcaById != null) {
                service.delete(marcaById);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Eliminado Correctamente")
                        .object(marcaById)
                        .build(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Id no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

}
