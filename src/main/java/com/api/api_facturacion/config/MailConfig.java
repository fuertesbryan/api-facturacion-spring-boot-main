package com.api.api_facturacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configuración dummy para evitar errores
        mailSender.setHost("localhost");
        mailSender.setPort(25);
        mailSender.setProtocol("smtp");
        return mailSender;
    }
}