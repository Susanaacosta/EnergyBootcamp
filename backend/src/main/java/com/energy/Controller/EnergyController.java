package com.energy.Controller;

import com.energy.Model.EnergyModel;
import com.energy.Repository.EnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/energy")
@CrossOrigin(origins = "*") // Pa que el Frontend no sea bloqueado por seguridad
public class EnergyController {

    @Autowired
    private EnergyRepository energyRepository;

    // Obtene absolutamente todo
    @GetMapping("/all")
    public List<EnergyModel> getAllEnergyData() {
        return energyRepository.findAll();
    }

    // Busca por ID específico
    @GetMapping("/{id}")
    public ResponseEntity<EnergyModel> getEnergyById(@PathVariable Long id) {
        Optional<EnergyModel> data = energyRepository.findById(id);
        return data.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*Producción total de energía renovable por tipo.
     * Esto le sirve al Front para hacer una gráficas DE TORTA
     */
    @GetMapping("/global-stats/{year}")
    public List<Object[]> getGlobalStats(@PathVariable Integer year) {
        return energyRepository.findGlobalProductionByYear(year);
    }

    /**Top 10 países con mayor producción eólica.
     * Esto le sirve al Front para hacer una gráfica de Barras.
     */
    @GetMapping("/top-wind/{year}")
    public List<Object[]> getTopWind(@PathVariable Integer year) {
        return energyRepository.findTop10WindProduction(year);
    }

    /**
     Tendencia histórica de capacidad solar.
     * Esto le sirve al Front para hacer una gráfica de Líneas
     */
    @GetMapping("/solar-trend/{country}")
    public List<Object[]> getSolarTrend(@PathVariable String country) {
        return energyRepository.findSolarCapacityTrend(country);
    }

    // Busca por País y Año exacto
    @GetMapping("/search")
    public ResponseEntity<EnergyModel> getByCountryAndYear(
            @RequestParam String entity, 
            @RequestParam Integer year) {
        
        return energyRepository.findByEntityAndDataYear(entity, year)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}