package com.cibertec.lp2.sesion9_thymel_jr.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.lp2.sesion9_thymel_jr.model.Usuario;
import com.cibertec.lp2.sesion9_thymel_jr.model.Venta;
import com.cibertec.lp2.sesion9_thymel_jr.model.VentaDetalle;
import com.cibertec.lp2.sesion9_thymel_jr.repository.UsuarioRepository;
import com.cibertec.lp2.sesion9_thymel_jr.repository.VentaRepository;
import com.cibertec.lp2.sesion9_thymel_jr.service.ReporteService;
import com.cibertec.lp2.sesion9_thymel_jr.service.UsuarioService;
import com.cibertec.lp2.sesion9_thymel_jr.service.VentaService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarVenta());
        return "venta-list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
    	 Venta venta = new Venta();
    	    venta.setFecha(LocalDateTime.now());
    	    venta.setUsuario(new Usuario());

    	    // mínimo 1 detalle
    	    venta.getDetalles().add(new VentaDetalle());
    	    
    	    model.addAttribute("venta", venta);
    	    model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "venta-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta) {
    	
    	ventaService.registrarVenta(venta);
    	
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("venta", ventaService.buscarVenta(id));
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "venta-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return "redirect:/ventas";
    }
    
    @GetMapping("/reporte/ver")
    public ResponseEntity<byte[]> previsualizar(){
    	byte[] pdf = reporteService.generarReporteVenta("pdf");

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=ventas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    @GetMapping("/reporte/descargar")
    public ResponseEntity<byte[]> descargarReporte() throws Exception {

        byte[] pdf = reporteService.generarReporteVenta("pdf");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=ventas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    @GetMapping("/reporte/verCustom")
    public ResponseEntity<byte[]> previsualizarCustom(){
    	byte[] pdf = reporteService.generarReporteVentasBean();

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=ventas2.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    /**
     * http://localhost:8080/ventas -> listado
     */
}
