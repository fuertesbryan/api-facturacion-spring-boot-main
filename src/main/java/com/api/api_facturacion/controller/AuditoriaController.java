package com.api.api_facturacion.controller;

import com.api.api_facturacion.model.AuditoriaFactura;
import com.api.api_facturacion.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class AuditoriaController {
    
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    
    @GetMapping
    public ResponseEntity<List<AuditoriaFactura>> obtenerTodasLasAuditorias() {
        List<AuditoriaFactura> auditorias = auditoriaRepository.findAll();
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/recientes")
    public ResponseEntity<List<AuditoriaFactura>> obtenerAuditoriasRecientes() {
        List<AuditoriaFactura> auditorias = auditoriaRepository.findTop50ByOrderByFechaAccionDesc();
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/factura/{facturaId}")
    public ResponseEntity<List<AuditoriaFactura>> obtenerAuditoriasPorFactura(@PathVariable Long facturaId) {
        List<AuditoriaFactura> auditorias = auditoriaRepository.findByFacturaId(facturaId);
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/accion/{accion}")
    public ResponseEntity<List<AuditoriaFactura>> obtenerAuditoriasPorAccion(@PathVariable String accion) {
        List<AuditoriaFactura> auditorias = auditoriaRepository.findByAccion(accion);
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/fechas")
    public ResponseEntity<List<AuditoriaFactura>> obtenerAuditoriasPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<AuditoriaFactura> auditorias = auditoriaRepository.findByFechaAccionBetween(inicio, fin);
        return ResponseEntity.ok(auditorias);
    }
}