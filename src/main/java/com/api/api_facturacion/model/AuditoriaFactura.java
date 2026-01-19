package com.api.api_facturacion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_facturas")
public class AuditoriaFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String accion; // CREATE, UPDATE, DELETE, READ
    
    private Long facturaId;
    
    private String numeroFactura;
    
    private String cliente;
    
    @Column(length = 1000)
    private String detalles;
    
    @Column(nullable = false)
    private LocalDateTime fechaAccion;
    
    private String usuario;
    
    // Constructor por defecto
    public AuditoriaFactura() {
        this.fechaAccion = LocalDateTime.now();
    }
    
    // Constructor con parámetros
    public AuditoriaFactura(String accion, Long facturaId, String numeroFactura, 
                           String cliente, String detalles) {
        this.accion = accion;
        this.facturaId = facturaId;
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.detalles = detalles;
        this.fechaAccion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    public Long getFacturaId() {
        return facturaId;
    }
    
    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }
    
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    
    public String getCliente() {
        return cliente;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getDetalles() {
        return detalles;
    }
    
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    
    public LocalDateTime getFechaAccion() {
        return fechaAccion;
    }
    
    public void setFechaAccion(LocalDateTime fechaAccion) {
        this.fechaAccion = fechaAccion;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}