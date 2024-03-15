package com.example.tienda.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.Entity.EstadoProductoEntity;
import com.example.tienda.Model.Service.IEstadoProducto;
import com.example.tienda.Payload.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EstadoProductoController {
    @Autowired
    private IEstadoProducto service;

    @PreAuthorize("hasAuthority('READ_ALL_ESTADO_PRODUCTO')")
    @GetMapping("/estadoproducto")
    public ResponseEntity<?> findAll() {
        try {
            List<EstadoProductoEntity> lista = service.findAll();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Estado de Productos")
                        .object(lista)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('READ_ALL_ACTIVE_ESTADO_PRODUCTO')")
    @GetMapping("/estadoproducto/activo")
    public ResponseEntity<?> findAllActivos() {
        try {
            List<EstadoProductoEntity> lista = service.findAllActive();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros Activos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Estado de Productos Activos")
                        .object(lista)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('READ_ALL_INACTIVE_ESTADO_PRODUCTO')")
    @GetMapping("/estadoproducto/inactivo")
    public ResponseEntity<?> findAllInactivos() {
        try {
            List<EstadoProductoEntity> lista = service.findAllFalse();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros Inactivos")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Estado de Productos Inactivos")
                        .object(lista)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('SEARCH_ESTADO_PRODUCTO_ID')")
    @GetMapping("/estadoproducto/{id}")
    public ResponseEntity<?> findByIdEstado(@PathVariable Integer id) {
        try {
            EstadoProductoEntity entity = service.findById(id);
            if (entity != null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Resultado de la busqueda")
                        .object(entity)
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('SAVE_ESTADO_PRODUCTO')")
    @PostMapping("/estadoproducto")
    public ResponseEntity<?> saveEstado(@Valid @RequestBody EstadoProductoEntity estado) {
        EstadoProductoEntity saveEntity = null;
        try {
            saveEntity = service.save(estado);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Guardado Exitosamente")
                    .object(saveEntity)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('EDIT_ESTADO_PRODUCTO')")
    @PutMapping("/estadoproducto/{id}")
    public ResponseEntity<?> UpdateEstado(@Valid @RequestBody EstadoProductoEntity estado, @PathVariable Integer id) {
        EstadoProductoEntity updateEntity = null;
        try {
            EstadoProductoEntity entity = service.findById(id);
            if (entity != null) {
                updateEntity = service.save(estado);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Actualizado Correctamente")
                        .object(updateEntity)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID")
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

    @PreAuthorize("hasAuthority('CHANGE_ACTIVIDAD_ESTADO_PRODUCTO')")
    @PatchMapping("/estadoproducto/{id}")
    public ResponseEntity<?> changeOfState(@PathVariable Integer id) {
        try {
            EstadoProductoEntity entity = service.findById(id);
            if (entity != null) {
                EstadoProductoEntity change = service.changeofStatus(entity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado Actualizado")
                        .object(change)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID")
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

    @PreAuthorize("hasAuthority('DELETE_ESTADO_PRODUCTO')")
    @DeleteMapping("/estadoproducto/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable Integer id) {
        try {
            EstadoProductoEntity entity = service.findById(id);
            if (entity != null) {
                service.delete(entity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Eliminado Correctamente")
                        .object(null)
                        .build(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontro el ID")
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
