package com.energy.Service;

import com.energy.Repository.EnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EnergyService {
    @Autowired
    private EnergyRepository energyRepository;

    // #1. Mapeo para Producción
    public List<Map<String, Object>> getProduction(int year) {
        List<Object[]> data = energyRepository.findProductionBySourceAndYear(year);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("region", row[0]);
            map.put("solar_twh", row[1]);
            map.put("wind_twh", row[2]);
            map.put("hydro_twh", row[3]);
            map.put("other_renewables_twh", row[4]);
            result.add(map);
        }
        return result;
    }

    // #2. Mapeo para Porcentaje
    public List<Map<String, Object>> getPercentage(int year) {
        List<Object[]> data = energyRepository.findRenewablePercentageByYear(year);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("region", row[0]);
            map.put("porcentaje_renovable", row[1]);
            result.add(map);
        }
        return result;
    }

    // #3. Mapeo para Tendencia Solar
    public List<Map<String, Object>> getSolarTrend() {
        List<Object[]> data = energyRepository.findGlobalSolarTrend();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("anio", row[0]);
            map.put("capacidad_solar_global", row[1]);
            result.add(map);
        }
        return result;
    }

    // #4. Mapeo para Top 10 Eólica
    public List<Map<String, Object>> getTop10Wind(int year) {
        List<Object[]> data = energyRepository.findTop10WindByYear(year);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("pais", row[0]);
            map.put("produccion_eolica_twh", row[1]);
            result.add(map);
        }
        return result;
    }

    // #5. Mapeo para Participación Global
    public Map<String, Object> getGlobalParticipation(int year) {
        List<Object[]> data = energyRepository.findGlobalParticipationByYear(year);
        Map<String, Object> map = new LinkedHashMap<>();
        if (!data.isEmpty()) {
            Object[] row = data.get(0);
            map.put("total_solar_mundial", row[0]);
            map.put("total_eolica_mundial", row[1]);
            map.put("total_hidro_mundial", row[2]);
            map.put("total_otras_renovables_mundial", row[3]);
        }
        return map;
    }
}