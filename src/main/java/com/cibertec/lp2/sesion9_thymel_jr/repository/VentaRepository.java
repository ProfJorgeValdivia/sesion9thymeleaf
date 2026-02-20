package com.cibertec.lp2.sesion9_thymel_jr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.lp2.sesion9_thymel_jr.dto.ReporteVentaDTO;
import com.cibertec.lp2.sesion9_thymel_jr.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
	
		@Query("""
			SELECT new com.cibertec.lp2.sesion9_thymel_jr.dto.ReporteVentaDTO(
			    v.id,
			    v.fecha,
			    v.cliente,
			    v.total,
			    u.nombre,
			    d.producto,
			    d.cantidad,
			    d.precio
			)
			FROM Venta v
			JOIN v.usuario u
			LEFT JOIN v.detalles d
			ORDER BY v.id, d.id
			""")
			List<ReporteVentaDTO> obtenerDataReporte();
}

