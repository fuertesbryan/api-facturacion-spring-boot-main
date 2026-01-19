package com.api.api_facturacion.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String numeroFactura;
    
    @Column(nullable = false)
    private String cliente;
    
    @Column(nullable = false)
    private String ruc;
    
    @Column(nullable = false)
    private Double subtotal;
    
    @Column(nullable = false)
    private Double iva;
    
    @Column(nullable = false)
    private Double total;
    
    @Column(nullable = false)
    private LocalDate fechaEmision;
    
    private LocalDate fechaVencimiento;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(nullable = false)
    private String estado; 
    
    private String formaPago; 
    
    public Factura() {
    }
    
    // Constructor completo
    public Factura(String numeroFactura, String cliente, String ruc, Double subtotal, 
                   Double iva, Double total, LocalDate fechaEmision, LocalDate fechaVencimiento,
                   String descripcion, String estado, String formaPago) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.ruc = ruc;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.descripcion = descripcion;
        this.estado = estado;
        this.formaPago = formaPago;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getRuc() {
        return ruc;
    }
    
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
    public Double getIva() {
        return iva;
    }
    
    public void setIva(Double iva) {
        this.iva = iva;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getFormaPago() {
        return formaPago;
    }
    
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
}