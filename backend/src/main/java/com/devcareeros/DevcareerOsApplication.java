package com.devcareeros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the entry point of the whole backend.
 *
 * @SpringBootApplication is a shortcut annotation that combines THREE
 * annotations in one:
 *   1. @Configuration     -> this class can define Spring beans
 *   2. @EnableAutoConfiguration -> Spring Boot auto-configures things like
 *      the embedded web server, JPA, etc., based on the dependencies in pom.xml
 *   3. @ComponentScan     -> Spring will scan this package (com.devcareeros)
 *      and all sub-packages for classes annotated with @Service,
 *      @RestController, @Repository, @Component, etc., and register them.
 *
 * Running main() starts an embedded Tomcat server on port 8080 (configured
 * in application.properties) and boots the whole Spring application context.
 */
@SpringBootApplication
public class DevcareerOsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevcareerOsApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" DevCareer OS backend is running!");
        System.out.println(" API base URL: http://localhost:8080/api");
        System.out.println("=========================================");
    }

}
