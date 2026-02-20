package com.cibertec.lp2.sesion9_thymel_jr.service;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.cibertec.lp2.sesion9_thymel_jr.dto.ReporteVentaDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteService {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private VentaService ventaService;
	
	
	public byte[] generarReporteVenta(String formato) {
		
		try {
		// 📌 Cargar reporte
        InputStream reporteStream =
                new ClassPathResource("reportes/reporte_ventas1.jasper").getInputStream();

        // 📌 Parámetros (si tu reporte usa)
        Map<String, Object> params = new HashMap<>();
        params.put("TITULO", "Reporte de ventas");

        // 📌 Conexión BD
        Connection conn = dataSource.getConnection();

        // 📌 Llenar reporte
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(reporteStream, params, conn);

        // 📌 Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
		}catch(IOException io) {
			io.printStackTrace();
			return null;
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] generarReporteVentasBean() {
		
		try {
		    List<ReporteVentaDTO> data = ventaService.obtenerDatosVenta(); 
		    // 👆 puedes usar @Query o armar manualmente
	
		    JRBeanCollectionDataSource dataSource =
		            new JRBeanCollectionDataSource(data);
	
		    Resource resource = new ClassPathResource("reportes/reporte_ventas2.jrxml");
	
		    JasperReport jasperReport = JasperCompileManager.compileReport(
		            resource.getInputStream());
	
		    Map<String, Object> params = new HashMap<>();
		    params.put("TITULO", "REPORTE DE VENTAS");
	
		    JasperPrint jasperPrint =
		            JasperFillManager.fillReport(jasperReport, params, dataSource);
	
		    return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Compilar desde JAVA
	 * @param formato
	 * @return
	 */
	public byte[] generarReporteVentaCompilar(String formato) {
        try (Connection conn = dataSource.getConnection()) {

            // 1. Cargar jrxml
            InputStream reportStream =
                    getClass().getResourceAsStream("/reportes/reporte_ventas1.jrxml");

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportStream);

            // 2. Parámetros

            // 3. Llenar reporte
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, null, conn);

            // 4. Exportar
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            if ("pdf".equalsIgnoreCase(formato)) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
            }

            return output.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte", e);
        }
    }
}
