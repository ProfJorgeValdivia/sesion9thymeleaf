package com.cibertec.lp2.sesion9_thymel_jr.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.lp2.sesion9_thymel_jr.dto.ReporteVentaDTO;
import com.cibertec.lp2.sesion9_thymel_jr.model.Usuario;
import com.cibertec.lp2.sesion9_thymel_jr.model.Venta;
import com.cibertec.lp2.sesion9_thymel_jr.model.VentaDetalle;
import com.cibertec.lp2.sesion9_thymel_jr.repository.UsuarioRepository;
import com.cibertec.lp2.sesion9_thymel_jr.repository.VentaRepository;

@Service
public class VentaService {

	@Autowired
	private VentaRepository ventaRepo;
	  
	@Autowired
	private UsuarioService usuarioService;
    
    public List<Venta> listarVenta(){
    	return ventaRepo.findAll();
    }
	
    @Transactional
	public void registrarVenta(Venta venta) {
		// reconstruir usuario
        Long usuarioId = venta.getUsuario().getId();
        Usuario usuario = usuarioService.buscarUsuario(usuarioId);
        venta.setUsuario(usuario);
        venta.setFecha(LocalDateTime.now());
        
        // VINCULAR CADA DETALLE CON LA VENTA
        for (VentaDetalle d : venta.getDetalles()) {
            d.setVenta(venta);
        }

        ventaRepo.save(venta); //
	}
	
	public Venta buscarVenta(Long id) {
		return ventaRepo.findById(id).orElseThrow();
	}
	
	public void eliminarVenta(Long id) {
		 ventaRepo.deleteById(id);
	}
	
	public List<ReporteVentaDTO> obtenerDatosVenta(){
		return ventaRepo.obtenerDataReporte();
	}
}
