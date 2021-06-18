# efmm-microservicios-store
Demo arquitectura de microservicios para tienda

## Spring Cloud Config
- msa-store-config

## Discovery Eureka
- msa-store-registry-service		

## Microservicios
- msa-store-customer
- msa-store-product
- msa-store-sale	( Implementacion de CircuitBreaker -> Metodos = getInvoice + createInvoice )

### Utilerias
- Feign
- Lombok
- H2
- JPA
- Devtools
- Validation

## Archivos de configuracion
- config-data

	Se declaran variables de conexión -> application.yml

```sh
	spring:
		cloud:
	    	config:
	      		server:
	        		git:
	          			uri: https://github.com/edfermtzmtz/efmm-microservicios-store.git
						searchPaths:  config-data
						username: ${GIT_USER}
						password: ${GIT_PASSWORD}
						default-label: main
```
Para el acceso a los archivos de configuracion solicitar fork y asi te permitira configuarar tus credenciales de Github

## Peticiones
- Configuracion http://root:s3cr3t@localhost:8081/discovery-service/default
- Discovery		http://localhost:8083
- Producto		http://localhost:8003/products
- Cliente		http://localhost:8004/customers
- Venta			http://localhost:8005/invoices

## Requirements

### Lombok
En los microservicios se hace uso de la Lombok por lo que se requiere su instalación
	[Descarga Lombok](https://projectlombok.org/)
