# Spring Boot Memcached Demo in Java

This is an example project showing the usage of [Memcached Spring Boot](https://github.com/sixhours-team/memcached-spring-boot) cache library in a Spring Boot Java application.
You will have to have Docker and Java 8 installed in order to run this demo.

There is an equivalent demo project written in Kotlin. To see version of this demo in Kotlin go to [Spring Boot Memcached Demo in Kotlin](https://github.com/sixhours-team/spring-boot-memcached-demo-kotlin).

## Testing

To run the application tests execute this command:

    ./gradlew test

## Running

First we have to start the Memcached server. In order to start it run:

    docker compose up -d

Now it's time to start the sample Spring Boot application:

    ./gradlew bootRun

You should now be able to access REST endpoints e.g.

1. *GET* http://localhost:8080/books (second request is cached response)

2. *GET* http://localhost:8080/books/Kotlin (second request is cached response)

3. *DELETE* http://localhost:8080/books/Kotlin (re-cached, next invocation of step 1. request will return list without book with title *Kotlin*)

To stop and remove the containers, run:

    docker compose down
