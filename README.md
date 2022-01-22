# ReadingIsGood


  ReadingIsGood is a Spring Boot backend application.
It mainly uses layered architecture by seperating model, service and controller layers from each other.
Entity layer represents the database layer, and JPA & Hibernate are used to implement it. In repository layer the data acquisition operations are performed.
Service layer represents where the business logic happens. Every controller calls a service method after a request from a user hits them. While implementing
services the Dependency Injection (DI) pattern is used.

For model scripting OpenAPI3 is used. Everytime the code is compiled it generates the client requests & responses by adding RIG- prefix in the beginning of their names. In this way, the service and client responses are not mixed with each other.

For securiy, Spring Security with JWT(JSON Web Token) is used, a Stateless Authentication mechanism is performed and every endpoint is secured by using this manner. LoginFilter is where the authentication starts, and after the user is authenticated a token is generated. This token is sent in requests with X-Auth-Token header. Any user having a non-expired JWT token can reach the endpoints.

For unit & integration tests JUnit & Mockito is used, most of the business cases has been tested mostly by the service tests.

The application is containerized using Docker, and it can easily be run with the following instructions.

In order to run the application: 

    sh run.sh
    
command can be run in the same directors as run.sh to execute the .sh file that comes in the project. If any problem occurs with the .sh file 

    mvn clean package spring-boot:repackage && docker-compose build && docker-compose up
    
command can be run in the same directory as the Dockerfile

The test portion of the project uses H2 database to run since when the above commands applied outside the container, it does not have the access to the internal database running in the container.

After running the project, you will be able to request it under:

    localhost:8080

And you can use the postman collection provided in the project to send requests.

Sometimes, some requests will naturally require the internal database ids in the request body, the database will be up and running on:

    jdbc:postgresql://localhost:5433/reading_db
    
By using the above connection url, you can connect to the DB by using your favourite DB-Client

