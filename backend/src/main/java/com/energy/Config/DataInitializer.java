package com.energy.Config;

import com.energy.Repository.EnergyRepository;
import com.energy.Service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EnergyRepository energyRepository;
    private final CsvService csvService;

    @Override
    public void run(String... args) {
        if (energyRepository.count() == 0) {
            System.out.println("No hay datos en energy_data. Cargando archivos CSV...");
            csvService.loadInternalCsv("installed_solar_capacity.csv", "SOLAR");
            csvService.loadInternalCsv("modern-renewable-prod.csv", "RENEWABLE");
            csvService.loadInternalCsv("share_electricity_renewables.csv", "SHARE");
        } else {
            System.out.println("La tabla energy_data ya contiene datos. No se recargan CSV.");
        }
    }
}