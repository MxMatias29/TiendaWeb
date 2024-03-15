package com.example.tienda.Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "carrito")
public class CarritoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long id_carrito;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UserEntity usuario;

    @Column(name = "fecha")
    private Date fecha;

    private double total = 0.0;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<ItemCarritoEntity> items = new ArrayList<>();

    // Por defecto debe ser true
    private Boolean aprobacion = false;

    private Integer total_productos = 0;

    private double total_precio;

    // Manejar el estado de la compra -> En proceso, Sin Stock, No procesado, En
    // camino, Entregado

}
