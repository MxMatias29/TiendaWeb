package com.example.tienda.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.Model.Entity.CarritoEntity;
import com.example.tienda.Model.Entity.ItemCarritoEntity;
import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Repository.UserRepository;
import com.example.tienda.Model.Service.ICarroOnline;
import com.example.tienda.Payload.MessageResponse;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class CarritoOnlineController {

    @Autowired
    private ICarroOnline carroService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('CART_ADD_ITEM')")
    @PostMapping("/cart-add-item")
    public ResponseEntity<?> agregarProductoCarrito(@RequestParam Integer idProducto, @RequestParam int cantidad) {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();
            if (carrito == null) {
                carrito = carroService.crearCarritoParaUsuarioAutenticado();
            }
            carroService.agregarProductoAlCarritoEnMemoria(carrito.getId_carrito(), idProducto, cantidad);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Producto Agregado Correctamente")
                    .object(null)
                    .build(), HttpStatus.OK);

        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUY_CART')")
    @PostMapping("/buy-cart")
    public ResponseEntity<?> finalizarCompra() {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();
            if (carrito != null) {
                carroService.finalizarCompra(carrito.getId_carrito());
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Compra Finalizada Correctamente")
                        .object(null)
                        .build(), HttpStatus.OK);
            }
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Carrito no encontrado")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MY_LIST_CART')")
    @GetMapping("/my-list-cart")
    public ResponseEntity<?> ObtenerElementosCarrito() {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();
            if (carrito != null) {
                List<ItemCarritoEntity> items = carroService.obtenerElementoCarritoItem(carrito.getId_carrito());
                List<Map<String, Object>> elementosCarrito = new ArrayList<>();

                for (ItemCarritoEntity item : items) {
                    Map<String, Object> elemento = new HashMap<>();
                    elemento.put("foto", item.getProducto().getFoto());
                    elemento.put("nombre", item.getProducto().getNombre());
                    elemento.put("descripcion", item.getProducto().getDescripcion());
                    elemento.put("cantidad", item.getCantidad());
                    elemento.put("precio", item.getPrecio());

                    elementosCarrito.add(elemento);
                }
                if (elementosCarrito.isEmpty()) {
                    return new ResponseEntity<>(MessageResponse.builder()
                            .message("No agrego Productos")
                            .object(null)
                            .build(), HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(MessageResponse.builder()
                            .message("Lista Encontrada")
                            .object(elementosCarrito)
                            .build(), HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Carrito no encontrado")
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

    @PreAuthorize("hasAuthority('BUY_HISTORY')")
    @GetMapping("/my-history")
    public ResponseEntity<?> obtenerHistorialCompras() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
            if (optionalUserEntity.isPresent()) {
                UserEntity usuario = optionalUserEntity.get();
                List<CarritoEntity> historialCompras = carroService.obtenerHistorialCompras(usuario.getId());
                List<Map<String, Object>> historialComprasResponse = new ArrayList<>();

                for (CarritoEntity compra : historialCompras) {
                    Map<String, Object> compraMap = new HashMap<>();
                    compraMap.put("fecha", compra.getFecha());
                    compraMap.put("total", compra.getTotal());
                    compraMap.put("Cantidad de Producto", compra.getTotal_productos());
                    historialComprasResponse.add(compraMap);
                }
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Historial Compras")
                        .object(historialComprasResponse)
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Usuario no encontrado")
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

    @PreAuthorize("hasAuthority('CLEAR_CART')")
    @DeleteMapping("/clear-cart")
    public ResponseEntity<?> vaciarCarrito() {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();

            if (carrito != null && !carrito.getAprobacion()) {
                carroService.vaciarCarrito(carrito.getId_carrito());
                return ResponseEntity.ok(MessageResponse.builder()
                        .message("Carrito vaciado exitosamente")
                        .build());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al vaciar el carrito: " + e.getMessage())
                    .build());
        }
    }

    @PreAuthorize("hasAuthority('VIEW_SALES_BETWEEN_DATES')")
    @GetMapping("/sales-between-dates")
    public ResponseEntity<?> buscarVentasEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        try {
            List<CarritoEntity> ventas = carroService.buscarVentasEntreFechas(fechaInicio, fechaFin);

            List<Map<String, Object>> ventasMap = new ArrayList<>();
            for (CarritoEntity venta : ventas) {
                Map<String, Object> ventaMap = new HashMap<>();
                ventaMap.put("Fecha de venta", venta.getFecha());

                List<Map<String, Object>> itemsVenta = new ArrayList<>();
                for (ItemCarritoEntity item : venta.getItems()) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("Producto", item.getProducto().getNombre());
                    itemMap.put("Cantidad", item.getCantidad());
                    itemMap.put("Precio", item.getPrecio());
                    itemsVenta.add(itemMap);
                }
                ventaMap.put("Items de Venta", itemsVenta);

                ventasMap.add(ventaMap);
            }
            return ResponseEntity.ok(ventasMap);

        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al buscar las ventas entre fechas: " + e.getMessage())
                    .build());
        }
    }

    @PreAuthorize("hasAuthority('VIEW_SALES_GREATER_THAN')")
    @GetMapping("/sales-greater-than")
    public ResponseEntity<?> buscarVentasMayorQue(@RequestParam double total) {
        try {
            List<CarritoEntity> ventas = carroService.buscarVentasMayorQue(total);
            List<Map<String, Object>> ventasMap = new ArrayList<>();
            for (CarritoEntity venta : ventas) {
                Map<String, Object> ventaMap = new HashMap<>();
                ventaMap.put("Fecha de venta", venta.getFecha());
                ventaMap.put("Total", venta.getTotal());
                List<Map<String, Object>> itemsVenta = new ArrayList<>();
                for (ItemCarritoEntity item : venta.getItems()) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("Producto", item.getProducto().getNombre());
                    itemMap.put("Cantidad", item.getCantidad());
                    itemMap.put("Precio", item.getPrecio());
                    itemsVenta.add(itemMap);
                }
                ventaMap.put("Items de Venta", itemsVenta);

                ventasMap.add(ventaMap);
            }
            return ResponseEntity.ok(ventasMap);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al buscar las ventas mayores que " + total + ": " + e.getMessage())
                    .build());
        }
    }

    @PreAuthorize("hasAuthority('VIEW_SALES_AFTER_DATE')")
    @GetMapping("/sales-after-date")
    public ResponseEntity<?> buscarVentasDespuesDe(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha) {
        try {
            List<CarritoEntity> ventas = carroService.buscarVentasDespuesDe(fecha);
            List<Map<String, Object>> ventasMap = new ArrayList<>();
            for (CarritoEntity venta : ventas) {
                Map<String, Object> ventaMap = new HashMap<>();
                ventaMap.put("Fecha de venta", venta.getFecha());
                ventaMap.put("Total", venta.getTotal());

                List<Map<String, Object>> itemsVenta = new ArrayList<>();
                for (ItemCarritoEntity item : venta.getItems()) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("Producto", item.getProducto().getNombre());
                    itemMap.put("Cantidad", item.getCantidad());
                    itemMap.put("Precio", item.getPrecio());
                    itemsVenta.add(itemMap);
                }
                ventaMap.put("Items de Venta", itemsVenta);

                ventasMap.add(ventaMap);
            }

            return ResponseEntity.ok(ventasMap);

        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al buscar las ventas después de la fecha " + fecha + ": " + e.getMessage())
                    .build());
        }
    }

    // Arreglar para hacer bucle for con map y que salga con el carrito item
    @PreAuthorize("hasAuthority('VIEW_ONLINE_SALES')")
    @GetMapping("/online-sales")
    public ResponseEntity<?> ventasOnline() {
        try {
            List<CarritoEntity> ventasOnline = carroService.ventasOnline();
            List<Map<String, Object>> ventasOnlineMap = new ArrayList<>();
            for (CarritoEntity venta : ventasOnline) {
                Map<String, Object> ventaMap = new HashMap<>();
                ventaMap.put("Fecha de venta", venta.getFecha());
                ventaMap.put("Total", venta.getTotal());

                List<Map<String, Object>> itemsVenta = new ArrayList<>();
                for (ItemCarritoEntity item : venta.getItems()) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("Producto", item.getProducto().getNombre());
                    itemMap.put("Cantidad", item.getCantidad());
                    itemMap.put("Precio", item.getPrecio());
                    itemsVenta.add(itemMap);
                }
                ventaMap.put("Items de Venta", itemsVenta);

                ventasOnlineMap.add(ventaMap);
            }
            return ResponseEntity.ok(ventasOnlineMap);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al obtener las ventas en línea: " + e.getMessage())
                    .build());
        }

    }

    @PreAuthorize("hasAuthority('DELETE_ITEM')")
    @DeleteMapping("/delete-item-cart/{idProducto}")
    public ResponseEntity<?> eliminarItemCarrito(@PathVariable Integer idProducto) {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();
            if (carrito != null) {
                carroService.eliminarItemCarrito(carrito.getId_carrito(), idProducto);
                return ResponseEntity.ok().body(MessageResponse.builder()
                        .message("Producto eliminado del carrito exitosamente")
                        .build());
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Carrito no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al eliminar el producto del carrito: " + e.getMessage())
                    .build());
        }
    }

    @PreAuthorize("hasAuthority('EDIT_STOCK')")
    @PatchMapping("/edit-cart-item/{productId}")
    public ResponseEntity<?> editarCantidadProductoCarrito(@PathVariable Integer productId,
            @RequestParam int newQuantity) {
        try {
            CarritoEntity carrito = obtenerCarritoUsuarioAutentiicado();

            if (carrito != null) {
                carroService.editarCantidadProductoCarrito(carrito.getId_carrito(), productId, newQuantity);
                return ResponseEntity.ok().body(MessageResponse.builder()
                        .message("Cantidad del producto en el carrito actualizada exitosamente")
                        .build());
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No se encontró el carrito asociado al usuario")
                        .object(null)
                        .build(), HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Error al eliminar el producto del carrito: " + e.getMessage())
                    .build());
        }
    }

    private CarritoEntity obtenerCarritoUsuarioAutentiicado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity usuario = optionalUserEntity.get();
            return carroService.obtenerCarritoUsuario(usuario.getId());
        }
        return null;
    }
}
