package com.cibertec.lp2.sesion9_thymel_jr.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteVentaDTO {
	private Long ventaId;
    private LocalDateTime fecha;
    private String cliente;
    private BigDecimal total;
    private String usuario;
    private String producto;
    private Integer cantidad;
    private BigDecimal precio;
    
    
	public ReporteVentaDTO() {
		super();
	}
	
	
	public ReporteVentaDTO(Long ventaId, LocalDateTime fecha, String cliente, BigDecimal total, String usuario,
			String producto, Integer cantidad, BigDecimal precio) {
		super();
		this.ventaId = ventaId;
		this.fecha = fecha;
		this.cliente = cliente;
		this.total = total;
		this.usuario = usuario;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
	}


	public Long getVentaId() {
		return ventaId;
	}


	public void setVentaId(Long ventaId) {
		this.ventaId = ventaId;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
    
    
}
