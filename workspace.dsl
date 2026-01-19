workspace {
    name "Sistema de Facturación API"
    description "Modelo C4 del API de Facturación desarrollado con Spring Boot"

    model {
        # Actores del sistema
        usuarioAdministrativo = person "Usuario Administrativo" "Persona encargada de gestionar las facturas de la empresa"
        clienteExterno = person "Cliente" "Cliente que consulta sus facturas emitidas"
        
        # Sistema externo
        sistemaContable = softwareSystem "Sistema Contable Externo" "Sistema ERP de la empresa que necesita información de facturación"
        
        # Nuestro sistema
        sistemaFacturacion = softwareSystem "Sistema de Facturación API" "Sistema que permite gestionar facturas" {
            
            # Contenedores
            aplicacionWeb = container "Aplicación Web" "Página web para gestionar facturas" "HTML y JavaScript" {
                tags "Navegador"
            }
            
            apiSpringBoot = container "API REST" "API que procesa las peticiones de facturación" "Spring Boot y Java" {
                tags "Aplicacion"
                
                # Componentes internos
                controlador = component "FacturaController" "Recibe las peticiones HTTP y devuelve respuestas" "Controlador REST" {
                    tags "Controlador"
                }
                
                repositorio = component "FacturaRepository" "Se conecta con la base de datos para guardar y consultar facturas" "Repositorio JPA" {
                    tags "Repositorio"
                }
                
                modelo = component "Factura" "Representa una factura con sus datos" "Entidad JPA" {
                    tags "Modelo"
                }
            }
            
            baseDatos = container "Base de Datos" "Guarda la información de las facturas" "Base de datos H2" {
                tags "BaseDatos"
            }
        }
        
        # Conexiones - Vista de Contexto
        usuarioAdministrativo -> sistemaFacturacion "Crea y gestiona facturas"
        clienteExterno -> sistemaFacturacion "Consulta sus facturas"
        sistemaContable -> sistemaFacturacion "Obtiene datos de facturación" "API REST"
        
        # Conexiones - Vista de Contenedores
        usuarioAdministrativo -> aplicacionWeb "Utiliza"
        clienteExterno -> aplicacionWeb "Utiliza"
        aplicacionWeb -> apiSpringBoot "Envía peticiones" "HTTP/JSON"
        sistemaContable -> apiSpringBoot "Consume datos" "HTTP/JSON"
        apiSpringBoot -> baseDatos "Lee y guarda facturas" "SQL"
        
        # Conexiones - Vista de Componentes
        aplicacionWeb -> controlador "Envía peticiones HTTP" "JSON"
        sistemaContable -> controlador "Consume API REST" "JSON"
        controlador -> repositorio "Utiliza para acceder a datos"
        repositorio -> baseDatos "Ejecuta consultas SQL" "JDBC"
        controlador -> modelo "Usa"
        repositorio -> modelo "Administra"
    }

    views {
        # Vista 1: Contexto
        systemContext sistemaFacturacion "VistaContexto" {
            include *
            autoLayout
            description "Muestra cómo los usuarios y otros sistemas interactúan con el Sistema de Facturación"
        }
        
        # Vista 2: Contenedores
        container sistemaFacturacion "VistaContenedores" {
            include *
            autoLayout
            description "Muestra las aplicaciones y bases de datos que componen el Sistema de Facturación"
        }
        
        # Vista 3: Componentes
        component apiSpringBoot "VistaComponentes" {
            include *
            autoLayout
            description "Muestra las partes internas del API REST (Controlador, Repositorio y Modelo)"
        }

        # Colores y formas
        styles {
            element "Person" {
                shape person
                background #1168bd
                color #ffffff
            }
            element "Software System" {
                background #2b7cb8
                color #ffffff
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
            element "BaseDatos" {
                shape cylinder
                background #438dd5
                color #ffffff
            }
            element "Navegador" {
                shape webbrowser
            }
            element "Controlador" {
                background #91C4F2
            }
            element "Repositorio" {
                background #7CB9E8
            }
            element "Modelo" {
                background #B0D9F1
            }
        }
    }
}