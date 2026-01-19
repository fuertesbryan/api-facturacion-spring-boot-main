package com.api.api_facturacion.service;

import com.api.api_facturacion.model.AuditoriaFactura;
import com.api.api_facturacion.model.Factura;
import com.api.api_facturacion.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    
    public void registrarAccion(String accion, Factura factura, String detalles) {
        AuditoriaFactura auditoria = new AuditoriaFactura(
            accion,
            factura.getId(),
            factura.getNumeroFactura(),
            factura.getCliente(),
            detalles
        );
        auditoriaRepository.save(auditoria);
    }
    
    public void registrarCreacion(Factura factura) {
        registrarAccion("CREATE", factura, 
            String.format("Factura creada - Total: $%.2f - Estado: %s", 
                factura.getTotal(), factura.getEstado()));
    }
    
    public void registrarActualizacion(Factura factura) {
        registrarAccion("UPDATE", factura, 
            String.format("Factura actualizada - Estado: %s - Total: $%.2f", 
                factura.getEstado(), factura.getTotal()));
    }
    
    public void registrarEliminacion(Factura factura) {
        registrarAccion("DELETE", factura, 
            String.format("Factura eliminada - Número: %s - Cliente: %s", 
                factura.getNumeroFactura(), factura.getCliente()));
    }
    
    public void registrarConsulta(Factura factura) {
        registrarAccion("READ", factura, 
            String.format("Factura consultada - ID: %d", factura.getId()));
    }
}