package com.energy.backend;

import com.energy.Service.EnergyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.energy"}) 
@EntityScan(basePackages = {"com.energy.Model"}) ////Verifico entre mayusculas y minusculas las rurtas de import
@EnableJpaRepositories(basePackages = {"com.energy.Repository"}) //Verifico entre mayusculas y minusculas las rurtas importt
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

   @Bean
    CommandLineRunner init(EnergyService energyService) {
        return args -> {
            try {
                System.out.println("Se Iniciando la carga de los datos...");
                
                // Nombre de los archivos csv
                String fileProd = "03 modern-renewable-prod.csv";
                String fileShare = "04 share-electricity-renewables.csv"; 
                String fileCap = "13 installed-solar-PV-capacity.csv";
                
                // Se pasa la informacion al metodo para que lo carga
                energyService.cargarTodo(fileProd, fileShare, fileCap);
                
                System.out.println("Carga Terminada");
            } catch (Exception e) {
                System.err.println("Se econtró error sobre la carga " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}