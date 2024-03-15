package com.example.tienda.Model.Service;

import java.util.Date;
import java.util.List;


import com.example.tienda.Model.Entity.CarritoEntity;
import com.example.tienda.Model.Entity.ItemCarritoEntity;

public interface ICarroOnline {
    void agregarProductoAlCarritoEnMemoria(Long idCarrito, Integer idProducto, int cantidad);

    void finalizarCompra(Long idCarrito);

    List<CarritoEntity> obtenerHistorialCompras(Long idUsuario);

    List<CarritoEntity> buscarVentasEntreFechas(Date fechaInicio, Date fechaFin);

    List<CarritoEntity> buscarVentasMayorQue(double total);

    List<CarritoEntity> buscarVentasDespuesDe(Date fecha);

    List<CarritoEntity> ventasOnline();

    void vaciarCarrito(Long idCarrito);

    List<ItemCarritoEntity> obtenerElementoCarritoItem(Long idCarrito);

    void eliminarItemCarrito(Long idCarrito, Integer idProducto);

    void editarCantidadProductoCarrito(Long idCarrito, Integer idProducto, int cantidadNueva);

    CarritoEntity obtenerCarritoUsuario(Long idUsuario);

    CarritoEntity crearCarritoParaUsuarioAutenticado();

}
