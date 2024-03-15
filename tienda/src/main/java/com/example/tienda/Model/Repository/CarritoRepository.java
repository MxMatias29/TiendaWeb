package com.example.tienda.Model.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.example.tienda.Model.Entity.CarritoEntity;
import com.example.tienda.Model.Entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends CrudRepository<CarritoEntity, Long> {

    // Busqueda por cliente -> Todos los carritos Asociados -> Historial de Compras
    // -> Admin - Cliente
    @Query("SELECT c FROM CarritoEntity c WHERE c.usuario = :usuario")
    List<CarritoEntity> findByUsuarioHistorial(UserEntity usuario);

    // Encuentra ventas creados despues de una fecha -> Admin
    List<CarritoEntity> findByFechaAfter(Date fecha);

    // Encuentra ventas creados despues de un rango de fechas -> Admin
    List<CarritoEntity> findByFechaBetween(Date fechaInicio, Date fechaFin);

    // Encuentra ventas mayores de un monto creado
    List<CarritoEntity> findByTotalGreaterThan(double total);


    // Ventas
    @Query(value = "SELECT * FROM carrito WHERE aprobacion = true", nativeQuery = true)
    List<CarritoEntity> findByAprobacionTrue();

    CarritoEntity findByUsuario(UserEntity usuario);

    Optional<CarritoEntity> findByUsuarioAndAprobacionFalse(UserEntity user);

    List<CarritoEntity> findByUsuarioAndAprobacionTrue(UserEntity user);

}
