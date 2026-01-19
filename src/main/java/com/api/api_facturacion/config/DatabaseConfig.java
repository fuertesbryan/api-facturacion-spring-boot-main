package com.api.api_facturacion.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
        } else if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            databaseUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
        }
        
        if (databaseUrl != null) {
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");
            
            return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .username(username)
                .password(password)
                .build();
        }
        
        return DataSourceBuilder.create().build();
    }
}