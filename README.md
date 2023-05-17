
# Backend para Spotify Api 

Aplicación web que permita a los usuarios buscar canciones
utilizando la API de Spotify, pero además implementar un sistema de autenticación de
usuario mediante JWT (JSON Web Token). Los usuarios deben poder registrarse, iniciar
sesión y guardar canciones en una lista de favoritos. Los usuarios autenticados podrán
acceder a su lista de favoritos en cualquier momento.

## Appendix
Si utilizaraas el frontend siguiente debes usar el puerto 8080

El frontend que pegara a este backend esta construido con angular en esta direccion : https://github.com/montoyafranco/spotify-api-v2


## Installation

Para correr esta app necesitas agregar las credenciales de adminstrador para tu base de datos de MySQL y se autogenera la DB bajo el nombre backend2 ,ejemplo
spring.datasource.url=jdbc:mysql://localhost:3306/backend2?createDatabaseIfNotExist=true cambiando luego de localhost:3306/backend2 por otro nombre es la base de datos que se creara



```bash
   # Configuración de la conexión a MySQL Aplication.properties
spring.datasource.url=jdbc:mysql://localhost:3306/backend2?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root


Esto no es necesario tocarlo a no ser que quieras hacer alguna cosa distinta con el comportamiento de la base de datos 
# Configuración de Hibernate/JPA
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.hbm2ddl.auto=update
```
    
## Documentation
Esta aplicacion esta documentada con Swagger 3 . Podras ver todos sus endpoints y como comunicarte con ella si no dispones de front

http://localhost:8080/swagger-ui/index.html#/


## Environment Variables

Momentaneamente el SECRET_KEY para generar y firmar JWT estara hardcodeado en el codigo hasta ser correctamente ocultada asi que no es necesario hacer nada.

SECRET_KEY=montoya_clave_secreta343434343434343434#




## Test

Aplicacion testeada en gran porcentage controlador y caso de uso 




## FAQ

#### Se puede hacer mejor el codigo ?

Si , por cuestiones de tiempo se puede testear a mayor profundidad y utilizar mejores arquitecturas. En este caso es Rest MVC .  Idealmente pronto sera refactorizado utilizando Clean Architecture
utilizando el plugin de bancolombia con un templete que yo prepare para utilizar de base . https://github.com/montoyafranco/bancolombia-clean-arch-gradle-mysql-java11 para su version 2.0.0
Su proxima version 3.0.0 seria escalarlo utilizando WebFlux para aprovechar java avanzado con reactividad y utilizar RabbitMQ como logger de la aplicacion o comunicacion con servicios externos. 
v4.0.0 seria hacer despliegue en AWS a EC2


