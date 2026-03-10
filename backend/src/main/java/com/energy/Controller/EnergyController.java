package com.energy.Controller;

import com.energy.Service.EnergyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/energy")
@Tag(name = "Dashboard Energético", description = "Consultas oficiales con resultados etiquetados (Key-Value)")
public class EnergyController {

    @Autowired
    private EnergyService energyService;

    @Operation(
        summary = "Consulta #1: Producción renovable por fuente", 
        description = "Retorna: region, solar_twh, wind_twh, hydro_twh, other_renewables_twh"
    )
    @GetMapping("/production")
    public List<Map<String, Object>> getProduction(@RequestParam int year) {
        return energyService.getProduction(year);
    }

    @Operation(
        summary = "Consulta #2: Porcentaje de energía renovable", 
        description = "Retorna: region, porcentaje_renovable"
    )
    @GetMapping("/percentage")
    public List<Map<String, Object>> getPercentage(@RequestParam int year) {
        return energyService.getPercentage(year);
    }

    @Operation(
        summary = "Consulta #3: Tendencia de capacidad solar", 
        description = "Retorna: anio, capacidad_solar_global"
    )
    @GetMapping("/solar-trend")
    public List<Map<String, Object>> getSolarTrend() {
        return energyService.getSolarTrend();
    }

    @Operation(
        summary = "Consulta #4: Top 10 países energía eólica", 
        description = "Retorna: pais, produccion_eolica_twh"
    )
    @GetMapping("/top-wind")
    public List<Map<String, Object>> getTop10Wind(@RequestParam int year) {
        return energyService.getTop10Wind(year);
    }

    @Operation(
        summary = "Consulta #5: Participación global por fuentes", 
        description = "Retorna un solo objeto con los totales mundiales de Solar, Eólica e Hidro"
    )
    @GetMapping("/global-participation")
    public Map<String, Object> getGlobalParticipation(@RequestParam int year) {
        return energyService.getGlobalParticipation(year);
    }
}