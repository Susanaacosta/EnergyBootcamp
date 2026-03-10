package com.energy.Config;

import com.energy.Service.CsvService;
import com.energy.Repository.EnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    @Autowired
    private EnergyRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // 1. Verificamos si la base de datos tiene información
        // Si hay registros pero son nulos, es mejor limpiar y reintentar
        if (repository.count() == 0) {
            System.out.println(">>> Base de datos vacía. Iniciando carga masiva de CSV...");

            // 2. RUTAS CORREGIDAS (Basadas en tu imagen: están directo en resources)
            String[] filePaths = {
                    "static/data/modern-renewable-prod.csv",
                    "static/data/share_electricity_renewables.csv",
                    "static/data/installed_solar_capacity.csv"
            };

            String[] energyTypes = { "production", "percentage", "capacity" };

            for (int i = 0; i < filePaths.length; i++) {
                try {
                    System.out.println("Intentando leer: " + filePaths[i]);
                    ClassPathResource resource = new ClassPathResource(filePaths[i]);

                    if (!resource.exists()) {
                        System.err.println(
                                "ERROR CRÍTICO: El archivo " + filePaths[i] + " no existe en src/main/resources");
                        continue;
                    }

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                        String line;
                        boolean isHeader = true;

                        while ((line = reader.readLine()) != null) {
                            if (isHeader) {
                                isHeader = false;
                                continue;
                            }

                            // IMPORTANTE: Si tus CSV usan punto y coma (;), cambia el "," por ";"
                            String[] data = line.split(",");

                            // Solo procesamos si la línea tiene datos (mínimo Entity, Code, Year)
                            if (data.length >= 3) {
                                csvService.processLine(data, energyTypes[i]);
                            }
                        }
                    }
                    System.out.println("Archivo [" + energyTypes[i] + "] cargado con éxito.");

                } catch (Exception e) {
                    System.err.println("Error en " + filePaths[i] + ": " + e.getMessage());
                }
            }
            System.out.println(">>> ¡Proceso de carga finalizado!");
        } else {
            System.out.println(
                    ">>> La base de datos ya tiene " + repository.count() + " registros. No se requiere carga.");
        }
    }
}