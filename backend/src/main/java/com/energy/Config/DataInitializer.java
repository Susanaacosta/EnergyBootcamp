package com.energy.Config;

import com.energy.Service.CsvService;
import com.energy.Repository.EnergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Clase que se usa para cargar los cvs que estan en resource
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EnergyRepository energyRepository;
    private final CsvService csvService;

    @Override
    public void run(String... args) {
        if (energyRepository.count() == 0) {
            csvService.loadInternalCsv("installed_solar_capacity.csv", "SOLAR");
            csvService.loadInternalCsv("modern-renewable-prod.csv", "RENEWABLE");
            csvService.loadInternalCsv("share_electricity_renewables.csv", "SHARE");
        }
    }
}