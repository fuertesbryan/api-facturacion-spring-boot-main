package com.api.api_facturacion.repository;

import com.api.api_facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    // Buscar por número de factura
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    
    // Buscar facturas por cliente
    List<Factura> findByClienteContainingIgnoreCase(String cliente);
    
    // Buscar facturas por RUC
    List<Factura> findByRuc(String ruc);
    
    // Buscar facturas por estado
    List<Factura> findByEstado(String estado);
    
    // Buscar facturas por rango de fechas
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar facturas por forma de pago
    List<Factura> findByFormaPago(String formaPago);
}