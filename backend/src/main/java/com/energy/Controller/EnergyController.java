package com.energy.Controller;

import com.energy.Service.EnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Creacion de las apis de los querys para acceder a su data
@RestController
@RequestMapping("/api/energy")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnergyController {

    private final EnergyService energyService;

    // 1. Producción total por tipo de energía
    @GetMapping("/stats/production-type")
    public ResponseEntity<List<Object[]>> getProductionByType() {
        return ResponseEntity.ok(energyService.getProductionByType());
    }

    // 2. Top 5 regiones con mayor consumo
    @GetMapping("/stats/top-consumption")
    public ResponseEntity<List<Object[]>> getTopConsumption() {
        return ResponseEntity.ok(energyService.getTopConsumption());
    }

    // 3. Promedio de capacidad por año
    @GetMapping("/stats/capacity-evolution")
    public ResponseEntity<List<Object[]>> getCapacityEvolution() {
        return ResponseEntity.ok(energyService.getCapacityEvolution());
    }

    // 4. Relación producción vs consumo por región
    @GetMapping("/stats/regional-compare")
    public ResponseEntity<List<Object[]>> getRegionalCompare() {
        return ResponseEntity.ok(energyService.getRegionalCompare());
    }

    // 5. Listar datos filtrados por tipo (ej. SOLAR, RENEWABLE)
    @GetMapping("/data/{type}")
    public ResponseEntity<?> getRawData(@PathVariable String type) {
        return ResponseEntity.ok(energyService.getDataByType(type));
    }
}