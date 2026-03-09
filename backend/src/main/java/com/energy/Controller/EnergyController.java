package com.energy.Controller;

import com.energy.DTO.CapacityEvolution;
import com.energy.DTO.RegionalCompare;
import com.energy.DTO.StatResponse;
import com.energy.Model.EnergyModel;
import com.energy.Model.EnergyType;
import com.energy.Service.EnergyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnergyController {

    private final EnergyService energyService;

    @Operation(summary = "Producción total por tipo de energía con filtros opcionales")
    @GetMapping("/stats/production-type")
    public ResponseEntity<List<StatResponse>> getProductionByType(

            @Parameter(description = "Año del registro, ejemplo: 2021")
            @RequestParam(required = false) Integer year,

            @Parameter(description = "País o región, ejemplo: Colombia")
            @RequestParam(required = false) String region,

            @Parameter(description = "Tipo de energía")
            @RequestParam(required = false) EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.getProductionByFilters(
                        year,
                        region,
                        type != null ? type.name() : null
                )
        );
    }

    @Operation(summary = "Top regiones con mayor consumo energético")
    @GetMapping("/stats/top-consumption")
    public ResponseEntity<List<StatResponse>> getTopConsumption(

            @Parameter(description = "Año del registro")
            @RequestParam(required = false) Integer year,

            @Parameter(description = "Tipo de energía")
            @RequestParam(required = false) EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.getConsumptionByFilters(
                        year,
                        type != null ? type.name() : null
                )
        );
    }

    @Operation(summary = "Evolución de la capacidad energética a través del tiempo")
    @GetMapping("/stats/capacity-evolution")
    public ResponseEntity<List<CapacityEvolution>> getCapacityEvolution(

            @Parameter(description = "País o región")
            @RequestParam(required = false) String region,

            @Parameter(description = "Tipo de energía")
            @RequestParam(required = false) EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.getCapacityEvolutionByFilters(
                        region,
                        type != null ? type.name() : null
                )
        );
    }

    @Operation(summary = "Comparación entre producción y consumo por región")
    @GetMapping("/stats/regional-compare")
    public ResponseEntity<List<RegionalCompare>> getRegionalCompare(

            @Parameter(description = "Año del registro")
            @RequestParam(required = false) Integer year,

            @Parameter(description = "Tipo de energía")
            @RequestParam(required = false) EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.getRegionalCompareByFilters(
                        year,
                        type != null ? type.name() : null
                )
        );
    }

    @Operation(summary = "Filtrar datos energéticos por año, región y tipo")
    @GetMapping("/filter")
    public ResponseEntity<List<EnergyModel>> filterData(

            @Parameter(description = "Año del registro")
            @RequestParam(required = false) Integer year,

            @Parameter(description = "País o región")
            @RequestParam(required = false) String region,

            @Parameter(description = "Tipo de energía")
            @RequestParam(required = false) EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.filterData(
                        year,
                        region,
                        type != null ? type.name() : null
                )
        );
    }

    @Operation(summary = "Obtener todos los datos por tipo de energía")
    @GetMapping("/data/{type}")
    public ResponseEntity<List<EnergyModel>> getRawData(

            @Parameter(description = "Tipo de energía: SOLAR, RENEWABLE, SHARE")
            @PathVariable EnergyType type
    ) {
        return ResponseEntity.ok(
                energyService.getDataByType(type.name())
        );
    }

    @Operation(summary = "Obtener lista de regiones disponibles")
    @GetMapping("/regions")
    public ResponseEntity<List<String>> getRegions() {
        return ResponseEntity.ok(energyService.getAllRegions());
    }

    @Operation(summary = "Obtener lista de años disponibles")
    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYears() {
        return ResponseEntity.ok(energyService.getAllYears());
    }

    @Operation(summary = "Obtener tipos de energía disponibles")
    @GetMapping("/types")
    public ResponseEntity<EnergyType[]> getEnergyTypes() {
        return ResponseEntity.ok(EnergyType.values());
    }
}