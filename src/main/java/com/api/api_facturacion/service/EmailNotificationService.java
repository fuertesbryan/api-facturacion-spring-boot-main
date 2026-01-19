package com.api.api_facturacion.service;

import com.api.api_facturacion.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${email.notificaciones.destinatario:}")
    private String destinatario;
    
    @Value("${spring.mail.username:}")
    private String remitente;
    
    public void enviarNotificacion(String asunto, String contenido) {
        if (destinatario == null || destinatario.isEmpty()) {
            System.out.println("⚠️ Email destinatario no configurado");
            return;
        }
        
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(remitente);
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(contenido);
            
            mailSender.send(mensaje);
            System.out.println("✅ Email enviado correctamente a: " + destinatario);
        } catch (Exception e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
        }
    }
    
    public void notificarCreacionFactura(Factura factura) {
        String asunto = "✅ Nueva Factura Creada - " + factura.getNumeroFactura();
        String contenido = String.format(
            "Se ha creado una nueva factura en el sistema:\n\n" +
            "════════════════════════════════════════\n" +
            "INFORMACIÓN DE LA FACTURA\n" +
            "════════════════════════════════════════\n\n" +
            "Número de Factura: %s\n" +
            "Cliente: %s\n" +
            "RUC: %s\n\n" +
            "DETALLE FINANCIERO:\n" +
            "----------------------------\n" +
            "Subtotal: $%.2f\n" +
            "IVA: $%.2f\n" +
            "TOTAL: $%.2f\n\n" +
            "INFORMACIÓN ADICIONAL:\n" +
            "----------------------------\n" +
            "Fecha de Emisión: %s\n" +
            "Fecha de Vencimiento: %s\n" +
            "Estado: %s\n" +
            "Forma de Pago: %s\n\n" +
            "Descripción: %s\n\n" +
            "════════════════════════════════════════\n" +
            "Sistema de Facturación - API REST\n",
            factura.getNumeroFactura(),
            factura.getCliente(),
            factura.getRuc(),
            factura.getSubtotal(),
            factura.getIva(),
            factura.getTotal(),
            factura.getFechaEmision(),
            factura.getFechaVencimiento(),
            factura.getEstado(),
            factura.getFormaPago(),
            factura.getDescripcion()
        );
        enviarNotificacion(asunto, contenido);
    }
    
    public void notificarActualizacionFactura(Factura factura) {
        String asunto = "📝 Factura Actualizada - " + factura.getNumeroFactura();
        String contenido = String.format(
            "Se ha actualizado una factura en el sistema:\n\n" +
            "════════════════════════════════════════\n" +
            "FACTURA ACTUALIZADA\n" +
            "════════════════════════════════════════\n\n" +
            "Número: %s\n" +
            "Cliente: %s\n" +
            "Estado: %s\n" +
            "Total: $%.2f\n" +
            "Fecha de Emisión: %s\n\n" +
            "════════════════════════════════════════\n" +
            "Sistema de Facturación - API REST\n",
            factura.getNumeroFactura(),
            factura.getCliente(),
            factura.getEstado(),
            factura.getTotal(),
            factura.getFechaEmision()
        );
        enviarNotificacion(asunto, contenido);
    }
    
    public void notificarEliminacionFactura(Factura factura) {
        String asunto = "🗑️ Factura Eliminada - " + factura.getNumeroFactura();
        String contenido = String.format(
            "Se ha eliminado una factura del sistema:\n\n" +
            "════════════════════════════════════════\n" +
            "FACTURA ELIMINADA\n" +
            "════════════════════════════════════════\n\n" +
            "Número: %s\n" +
            "Cliente: %s\n" +
            "Total: $%.2f\n" +
            "Estado: %s\n\n" +
            "════════════════════════════════════════\n" +
            "Sistema de Facturación - API REST\n",
            factura.getNumeroFactura(),
            factura.getCliente(),
            factura.getTotal(),
            factura.getEstado()
        );
        enviarNotificacion(asunto, contenido);
    }
}