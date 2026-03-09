package com.energy.backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.energy") 
@EnableJpaRepositories(basePackages = "com.energy.Repository")
// ERROR COMÚN: Asegúrate de que tus entidades estén en .Model o en .Entity y corrígelo aquí:
@EntityScan(basePackages = {"com.energy.Model", "com.energy.Entity"}) 
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}