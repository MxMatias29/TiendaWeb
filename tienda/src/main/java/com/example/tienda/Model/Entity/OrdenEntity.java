package com.example.tienda.Model.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="orden")
public class OrdenEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orden_id;
	
	@Email
	@Column(nullable = false)
	private String correo;

	@OneToMany(mappedBy = "orden", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<OrdenDetalle> orderItems = new ArrayList<>();

	private LocalDate orden_fecha;
	
	@OneToOne
	@JoinColumn(name = "pago_id")
	private PagoEntity payment;
	
	private Double total_cantidad;
	private String orden_estado;
}
