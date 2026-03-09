package com.energy.Service;

import com.energy.Model.EnergyModel;
import com.energy.Repository.EnergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final EnergyRepository energyRepository;

    public void loadInternalCsv(String fileName, String type) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            List<EnergyModel> list = new ArrayList<>();
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                
                // Usamos un límite alto en split para que no ignore celdas vacías al final
                String[] v = line.split(",", -1); 
                
                try {
                    EnergyModel data = new EnergyModel();
                    data.setEnergyType(type);
                    data.setRegion(v[0]); // El nombre del país suele ser la columna 0

                    
                    if (fileName.contains("modern-renewable-prod")) {
                        // Estructura: Entity, Code, Year, Production
                        data.setYearData(Integer.parseInt(v[2].trim()));
                        data.setProduction(parseSafeDouble(v[3]));
                    } 
                    else if (fileName.contains("installed_solar_capacity")) {
                        // Estructura: Entity, Code, Year, Capacity
                        data.setYearData(Integer.parseInt(v[2].trim()));
                        data.setCapacity(parseSafeDouble(v[3]));
                    }
                    else if (fileName.contains("share_electricity_renewables")) {
                        // Estructura: Entity, Code, Year, % (Consumption/Share)
                        data.setYearData(Integer.parseInt(v[2].trim()));
                        data.setConsumption(parseSafeDouble(v[3]));
                    }
                    else {
                        // Mapeo genérico por si tienes otros
                        data.setProduction(parseSafeDouble(v[1]));
                        data.setYearData(Integer.parseInt(v[4].trim()));
                    }

                    list.add(data);
                } catch (Exception ex) {
                    // Esto evita que una sola fila dañada detenga toda la carga
                    continue; 
                }
            }
            energyRepository.saveAll(list);
            System.out.println("Cargado exitosamente: " + fileName);
        } catch (Exception e) {
            System.err.println("Error crítico cargando " + fileName + ": " + e.getMessage());
        }
    }

    // Método auxiliar para evitar el error de "empty String"
    private double parseSafeDouble(String val) {
        if (val == null || val.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(val.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}