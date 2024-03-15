package com.example.tienda.Model.Service.Implementacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda.Model.Entity.CarritoEntity;
import com.example.tienda.Model.Entity.ItemCarritoEntity;
import com.example.tienda.Model.Entity.ProductoEntity;
import com.example.tienda.Model.Entity.UserEntity;
import com.example.tienda.Model.Repository.CarritoRepository;
import com.example.tienda.Model.Repository.ItemCarritoRepository;
import com.example.tienda.Model.Repository.ProductoRepository;
import com.example.tienda.Model.Repository.UserRepository;
import com.example.tienda.Model.Service.ICarroOnline;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CarritoOnlineImpl implements ICarroOnline {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void agregarProductoAlCarritoEnMemoria(Long idCarrito, Integer idProducto, int cantidad) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();
            Optional<ProductoEntity> optionalProducto = productoRepository.findById(idProducto);
            if (optionalProducto.isPresent()) {
                ProductoEntity producto = optionalProducto.get();

                if (verificarStock(producto, cantidad)) {
                    if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
                        carrito.setItems(new ArrayList<>());
                    }

                    ItemCarritoEntity itemCarrito = new ItemCarritoEntity();
                    itemCarrito.setProducto(producto);
                    itemCarrito.setCantidad(cantidad);
                    itemCarrito.setPrecio(producto.getPrecio() * cantidad);
                    itemCarrito.setCarrito(carrito);
                    itemCarritoRepository.save(itemCarrito);

                    carrito.getItems().add(itemCarrito);
                    carritoRepository.save(carrito);
                } else {
                    System.out.println("No hay stock");
                }
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    @Transactional
    @Override
    public void finalizarCompra(Long idCarrito) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);

        if (optionalCarrito.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();

            carrito.setFecha(new Date());
            double totalCompra = calcularTotalCompra(carrito);
            double totalConImpuesto = totalCompra + (totalCompra * 0.05);
            int totalProductos = contarTotalProductos(carrito);

            for (ItemCarritoEntity item : carrito.getItems()) {
                ProductoEntity producto = item.getProducto();
                int cantidad = item.getCantidad();
                producto.setStock(producto.getStock() - cantidad);
            }

            carrito.setTotal(totalConImpuesto);
            carrito.setTotal_precio(totalCompra);
            carrito.setTotal_productos(totalProductos);
            carrito.setAprobacion(true);
            carritoRepository.save(carrito);

            carrito.getItems().clear();
            CarritoEntity nuevoCarrito = new CarritoEntity();
            nuevoCarrito.setUsuario(carrito.getUsuario());
            carritoRepository.save(nuevoCarrito);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarritoEntity> obtenerHistorialCompras(Long idUsuario) {
        Optional<UserEntity> optionalUser = userRepository.findById(idUsuario);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return carritoRepository.findByUsuarioAndAprobacionTrue(user);
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarritoEntity> buscarVentasEntreFechas(Date fechaInicio, Date fechaFin) {
        return carritoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarritoEntity> buscarVentasMayorQue(double total) {
        return carritoRepository.findByTotalGreaterThan(total);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarritoEntity> buscarVentasDespuesDe(Date fecha) {
        return carritoRepository.findByFechaAfter(fecha);
    }

    @Override
    public List<CarritoEntity> ventasOnline() {
        return carritoRepository.findByAprobacionTrue();
    }

    @Transactional
    @Override
    public void vaciarCarrito(Long idCarrito) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();
            List<ItemCarritoEntity> items = carrito.getItems();
            items.forEach(item -> itemCarritoRepository.delete(item));
            items.clear(); // Limpiar la lista después de eliminar los elementos

            carritoRepository.save(carrito);
        } else {
            System.out.println("No se encontro el carrito ");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemCarritoEntity> obtenerElementoCarritoItem(Long idCarrito) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();
            if (!carrito.getAprobacion()) {
                return carrito.getItems();
            }
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public void eliminarItemCarrito(Long idCarrito, Integer idProducto) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();
            List<ItemCarritoEntity> items = carrito.getItems();

            // Buscar el ítem del carrito por el id del producto
            Optional<ItemCarritoEntity> itemToRemove = items.stream()
                    .filter(item -> item.getProducto().getId_productos().equals(idProducto))
                    .findFirst();

            if (itemToRemove.isPresent()) {
                ItemCarritoEntity item = itemToRemove.get();
                itemCarritoRepository.delete(item); // Eliminar el ítem del carrito de la base de datos
                items.remove(item); // Eliminar el ítem del carrito de la lista de ítems
                carritoRepository.save(carrito); // Guardar los cambios en el carrito
            } else {
                System.out.println("El producto no está en el carrito.");
            }
        } else {
            System.out.println("No se encontró el carrito.");
        }
    }

    @Transactional
    @Override
    public void editarCantidadProductoCarrito(Long idCarrito, Integer idProducto, int cantidadNueva) {
        Optional<CarritoEntity> optionalCarrito = carritoRepository.findById(idCarrito);
        Optional<ProductoEntity> optionalProducto = productoRepository.findById(idProducto);

        if (optionalCarrito.isPresent() && optionalProducto.isPresent()) {
            CarritoEntity carrito = optionalCarrito.get();
            ProductoEntity producto = optionalProducto.get();

            List<ItemCarritoEntity> items = carrito.getItems();
            for (ItemCarritoEntity item : items) {
                if (item.getProducto().getId_productos().equals(idProducto)) {
                    item.setCantidad(cantidadNueva);
                    item.setPrecio(producto.getPrecio() * cantidadNueva);
                    break;
                }
            }

            carritoRepository.save(carrito);
        } else {
            throw new EntityNotFoundException("No se encontró el carrito o el producto especificado");
        }
    }

    @Transactional
    @Override
    public CarritoEntity obtenerCarritoUsuario(Long idUsuario) {
        Optional<UserEntity> optionalUser = userRepository.findById(idUsuario);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return carritoRepository.findByUsuarioAndAprobacionFalse(user).orElse(null);
        } else {
            System.out.println("Carrito no encontrado");
            return null;
        }
    }

    // Verifica stock
    private boolean verificarStock(ProductoEntity producto, int cantidad) {
        return producto != null && producto.getStock() >= cantidad && cantidad > 0;
    }

    // Calcular total compra
    private double calcularTotalCompra(CarritoEntity carrito) {
        double total = 0.0;
        for (ItemCarritoEntity item : carrito.getItems()) {
            total += item.getPrecio();
        }
        return total;
    }

    // Calcular total compra
    private int contarTotalProductos(CarritoEntity carrito) {
        int total = 0;
        for (ItemCarritoEntity item : carrito.getItems()) {
            total += item.getCantidad();
        }
        return total;
    }

    @Transactional
    @Override
    public CarritoEntity crearCarritoParaUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity usuario = optionalUserEntity.get();
            CarritoEntity carrito = new CarritoEntity();
            carrito.setUsuario(usuario);
            return carritoRepository.save(carrito);
        }
        return null;
    }

}