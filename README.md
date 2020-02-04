# SpringBoot para GlobalLogic Test CRUD - JWT - RESTFUL - H2

Este es un proyecto de SpringBoot para GlobalLogic

Pre-requisitos tener instalado:

    -   Gradle: https://gradle.org/
    -   Postman
    -   jdk8

Inicie un shell (prompt), luego desde la ruta de instalación del proyecto (GitHub) debe compilar con Gradle el proyecto:

    - gradle build

Para ejecutarlo:

    - gradle run
 
Nota:    
    
    Para probar el microservicio RestFul se debee importar el Script de Postman: GLOBALLOGIC_CRUD_TEST.postman_collection.json  

Pasos para Probar:

   1. Una vez cargado el archivo de plantilla de Postman GLOBALLOGIC_CRUD_TEST.postman_collection.json ejecutar el request "Login OAUTH JWT"
   2. La respuesta de la invocacion devolvera un archivo JSON, en el se debe tomar el valor del atributo "access_token"
   3. Ese valor es el token JWT (caduca en 1 hora)
   4. Para probar las operaciones del CRUD se debe colocar el token obtenido en la cabecera "Authorization" seleccionado en "TYPE" el que
   dice "Bearer Token" y oprimir el boton "Preview Request", esto hay que hacerlo a los request de postman que terminan en JWT (OJO).
   5. Ya con el token JWT seteado se pueden probar los demas request de Postman  
   

Para Acceder a la Base de Datos H2 (Embebida en memoria), usar las credenciales:

    - http://localhost:8080/h2-console/
    - user: sa
    - pass: sa

 