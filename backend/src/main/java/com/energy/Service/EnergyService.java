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
public class EnergyService {

    private final EnergyRepository energyRepository;

    public void loadCsvData(String fileName, String type) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            List<EnergyModel> list = new ArrayList<>();
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                
                // Usamos una expresión regular para separar por comas, ignorando comas dentro de comillas
                String[] v = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                if (v.length < 4) continue; // Saltar líneas vacías o incompletas

                try {
                    EnergyModel data = new EnergyModel();
                    
                    // Ajuste de índices para CSVs típicos (Entity, Code, Year, Value)
                    // v[0] suele ser el País/Región
                    // v[2] suele ser el Año
                    // v[3] suele ser el Valor (Producción/Consumo/Capacidad)
                    
                    data.setRegion(v[0].replace("\"", "")); 
                    data.setYearData(Integer.parseInt(v[2].trim()));
                    
                    double value = Double.parseDouble(v[3].trim());
                    data.setProduction(value); // Mapeamos el valor principal
                    data.setEnergyType(type);
                    
                    // Inicializamos los otros en 0 para evitar nulos en Postgres
                    data.setConsumption(0.0);
                    data.setCapacity(value); 

                    list.add(data);
                } catch (Exception e) {
                    // Si una línea falla (como las que tienen "AFG" en el lugar equivocado), la saltamos
                    continue;
                }
            }
            energyRepository.saveAll(list);
            System.out.println("✅ Importados " + list.size() + " registros de " + fileName);
        } catch (Exception e) {
            System.err.println("❌ Error crítico en " + fileName + ": " + e.getMessage());
        }
    }

    // Métodos para el Controller
    public List<Object[]> getProductionByType() { return energyRepository.getTotalProductionByType(); }
    public List<Object[]> getTopConsumption() { return energyRepository.getTopConsumingRegions(); }
    public List<Object[]> getCapacityEvolution() { return energyRepository.getAverageCapacityByYear(); }
    public List<Object[]> getRegionalCompare() { return energyRepository.getProductionVsConsumption(); }
    public List<EnergyModel> getDataByType(String type) { return energyRepository.findByEnergyType(type); }
}