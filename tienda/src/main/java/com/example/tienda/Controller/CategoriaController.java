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

import com.example.tienda.Model.Entity.CategoriaEntity;
import com.example.tienda.Model.Service.ICategoria;
import com.example.tienda.Payload.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private ICategoria service;

    @PreAuthorize("hasAuthority('READ_ALL_CATEGORY')")
    @GetMapping("/categoria")
    public ResponseEntity<?> findAll() {
        try {
            List<CategoriaEntity> listAll = service.findAll();
            if (listAll.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Lista de Categorias")
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

    @PreAuthorize("hasAuthority('READ_ALL_ACTIVE_CATEGORY')")
    @GetMapping("/categoria/activo")
    public ResponseEntity<?> findAllActive() {
        try {
            List<CategoriaEntity> listAllActives = service.findAllActive();
            if (listAllActives.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Categorias Activas")
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

    @PreAuthorize("hasAuthority('READ_ALL_INACTIVE_CATEGORY')")
    @GetMapping("/categoria/inactivo")
    public ResponseEntity<?> findAllFalse() {
        try {
            List<CategoriaEntity> listAllActives = service.findAllFalse();
            if (listAllActives.isEmpty()) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Categorias Inactivos")
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

    @PreAuthorize("hasAuthority('SEARCH_CATEGORY_ID')")
    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            CategoriaEntity category = service.findById(id);
            if (category == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No hay registros")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Categoria Encontrada")
                        .object(category)
                        .build(), HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('SAVE_CATEGORY')")
    @PostMapping("/categoria")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoriaEntity categoryEntity) {
        CategoriaEntity category = null;
        try {
            category = service.save(categoryEntity);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Categoria Guardada")
                    .object(category)
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('EDIT_CATEGORY')")
    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> updateCategory(@Valid @PathVariable Integer id,
            @RequestBody CategoriaEntity categoryEntity) {
        CategoriaEntity categoryUpdate = null;
        try {
            CategoriaEntity categoryById = service.findById(id);
            if (categoryById != null) {
                categoryUpdate = service.save(categoryEntity);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Registro actualizado")
                        .object(categoryUpdate)
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

    @PreAuthorize("hasAuthority('CHANGE_ACTIVIDAD_CATEGORY')")
    @PatchMapping("/categoria/{id}")
    public ResponseEntity<?> changeOfState(@PathVariable Integer id) {
        try {
            CategoriaEntity categoryId = service.findById(id);
            if (categoryId != null) {
                CategoriaEntity categorieState = service.changeofStatus(categoryId);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Estado actualizado")
                        .object(categorieState)
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

    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            CategoriaEntity categoryById = service.findById(id);
            if (categoryById != null) {
                service.delete(categoryById);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Se elimino correctamente la categoria")
                        .object(categoryById)
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
