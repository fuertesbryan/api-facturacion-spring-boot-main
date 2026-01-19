package com.api.api_facturacion.repository;

import com.api.api_facturacion.model.AuditoriaFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<AuditoriaFactura, Long> {
    
    List<AuditoriaFactura> findByFacturaId(Long facturaId);
    
    List<AuditoriaFactura> findByAccion(String accion);
    
    List<AuditoriaFactura> findByFechaAccionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<AuditoriaFactura> findTop50ByOrderByFechaAccionDesc();
}