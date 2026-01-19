package com.api.api_facturacion.controller;

import com.api.api_facturacion.model.Factura;
import com.api.api_facturacion.repository.FacturaRepository;
import com.api.api_facturacion.service.AuditoriaService;
import com.api.api_facturacion.service.EmailNotificationService;
import com.api.api_facturacion.service.SlackNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*") 
public class FacturaController {
    
    @Autowired
    private FacturaRepository facturaRepository;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Autowired
    private EmailNotificationService emailService;
    
    @Autowired
    private SlackNotificationService slackService;
    
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        
        if (factura.isPresent()) {
            auditoriaService.registrarConsulta(factura.get());
        }
        
        return factura.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        try {
            Optional<Factura> facturaExistente = facturaRepository.findByNumeroFactura(factura.getNumeroFactura());
            if (facturaExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            Factura nuevaFactura = facturaRepository.save(factura);
            
            // Registrar auditoría
            auditoriaService.registrarCreacion(nuevaFactura);
            
            // Enviar notificaciones asíncronas
            emailService.notificarCreacionFactura(nuevaFactura);
            slackService.notificarCreacionFactura(nuevaFactura);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Long id, @RequestBody Factura facturaActualizada) {
        Optional<Factura> facturaOptional = facturaRepository.findById(id);
        
        if (facturaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Factura factura = facturaOptional.get();
        factura.setCliente(facturaActualizada.getCliente());
        factura.setRuc(facturaActualizada.getRuc());
        factura.setSubtotal(facturaActualizada.getSubtotal());
        factura.setIva(facturaActualizada.getIva());
        factura.setTotal(facturaActualizada.getTotal());
        factura.setFechaEmision(facturaActualizada.getFechaEmision());
        factura.setFechaVencimiento(facturaActualizada.getFechaVencimiento());
        factura.setDescripcion(facturaActualizada.getDescripcion());
        factura.setEstado(facturaActualizada.getEstado());
        factura.setFormaPago(facturaActualizada.getFormaPago());
        
        Factura facturaGuardada = facturaRepository.save(factura);
        
        // Registrar auditoría
        auditoriaService.registrarActualizacion(facturaGuardada);
        
        // Enviar notificaciones
        emailService.notificarActualizacionFactura(facturaGuardada);
        slackService.notificarActualizacionFactura(facturaGuardada);
        
        return ResponseEntity.ok(facturaGuardada);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        
        if (factura.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Factura facturaAEliminar = factura.get();
        
        // Registrar auditoría antes de eliminar
        auditoriaService.registrarEliminacion(facturaAEliminar);
        
        // Enviar notificaciones
        emailService.notificarEliminacionFactura(facturaAEliminar);
        slackService.notificarEliminacionFactura(facturaAEliminar);
        
        facturaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/numero/{numeroFactura}")
    public ResponseEntity<Factura> buscarPorNumeroFactura(@PathVariable String numeroFactura) {
        Optional<Factura> factura = facturaRepository.findByNumeroFactura(numeroFactura);
        return factura.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<Factura>> buscarPorCliente(@PathVariable String cliente) {
        List<Factura> facturas = facturaRepository.findByClienteContainingIgnoreCase(cliente);
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<List<Factura>> buscarPorRuc(@PathVariable String ruc) {
        List<Factura> facturas = facturaRepository.findByRuc(ruc);
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Factura>> buscarPorEstado(@PathVariable String estado) {
        List<Factura> facturas = facturaRepository.findByEstado(estado);
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/fechas")
    public ResponseEntity<List<Factura>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<Factura> facturas = facturaRepository.findByFechaEmisionBetween(inicio, fin);
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/pago/{formaPago}")
    public ResponseEntity<List<Factura>> buscarPorFormaPago(@PathVariable String formaPago) {
        List<Factura> facturas = facturaRepository.findByFormaPago(formaPago);
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasFacturas> obtenerEstadisticas() {
        List<Factura> todasFacturas = facturaRepository.findAll();
        
        long totalFacturas = todasFacturas.size();
        double montoTotal = todasFacturas.stream()
                .mapToDouble(Factura::getTotal)
                .sum();
        
        long facturasPendientes = todasFacturas.stream()
                .filter(f -> "PENDIENTE".equals(f.getEstado()))
                .count();
        
        long facturasPagadas = todasFacturas.stream()
                .filter(f -> "PAGADA".equals(f.getEstado()))
                .count();
        
        EstadisticasFacturas stats = new EstadisticasFacturas(
                totalFacturas, 
                montoTotal, 
                facturasPendientes, 
                facturasPagadas
        );
        
        return ResponseEntity.ok(stats);
    }
    
    public static class EstadisticasFacturas {
        private long totalFacturas;
        private double montoTotal;
        private long facturasPendientes;
        private long facturasPagadas;
        
        public EstadisticasFacturas(long totalFacturas, double montoTotal, 
                                   long facturasPendientes, long facturasPagadas) {
            this.totalFacturas = totalFacturas;
            this.montoTotal = montoTotal;
            this.facturasPendientes = facturasPendientes;
            this.facturasPagadas = facturasPagadas;
        }
        
        public long getTotalFacturas() { return totalFacturas; }
        public double getMontoTotal() { return montoTotal; }
        public long getFacturasPendientes() { return facturasPendientes; }
        public long getFacturasPagadas() { return facturasPagadas; }
    }
}