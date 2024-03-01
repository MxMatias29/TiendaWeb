package com.example.tienda.Model.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "productos")
public class ProductoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id_productos")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_productos;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    @NotEmpty
    @Size(max = 100)
    private String nombre;

    @Column(name = "descripcion")
    @NotEmpty
    @Size(max = 200)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private MarcaEntity marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private ProveedorEntity proveedor;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoProductoEntity estado;

    @Column(name = "stock")
    @NotNull
    private Integer stock;

    @Column(name = "stock_minimo")
    @NotNull
    private Integer stock_minimo;

    @Column(name = "precio_neto")
    @NotNull
    private float precio_neto;

    @Column(name = "precio_bruto")
    @NotNull
    private float precio_bruto;

    @Column(name = "codigobarra")
    @NotEmpty
    private String codigobarra; 

    @Column(name = "actividad")
    private Boolean actividad;
}
