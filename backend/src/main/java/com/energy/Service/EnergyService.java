package com.energy.Service;

import com.energy.DTO.CapacityEvolution;
import com.energy.DTO.RegionalCompare;
import com.energy.DTO.StatResponse;
import com.energy.Model.EnergyModel;
import com.energy.Repository.EnergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyService {

    private final EnergyRepository energyRepository;

    public List<StatResponse> getProductionByFilters(Integer year, String region, String type) {
        return energyRepository.getProductionByFilters(year, region, type)
                .stream()
                .map(row -> new StatResponse(
                        row[0] != null ? row[0].toString() : "",
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0.0
                ))
                .toList();
    }

    public List<StatResponse> getConsumptionByFilters(Integer year, String type) {
        return energyRepository.getConsumptionByFilters(year, type)
                .stream()
                .map(row -> new StatResponse(
                        row[0] != null ? row[0].toString() : "",
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0.0
                ))
                .toList();
    }

    public List<CapacityEvolution> getCapacityEvolutionByFilters(String region, String type) {
        return energyRepository.getCapacityEvolutionByFilters(region, type)
                .stream()
                .map(row -> new CapacityEvolution(
                        row[0] != null ? ((Number) row[0]).intValue() : 0,
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0.0
                ))
                .toList();
    }

    public List<RegionalCompare> getRegionalCompareByFilters(Integer year, String type) {
        return energyRepository.getRegionalCompareByFilters(year, type)
                .stream()
                .map(row -> new RegionalCompare(
                        row[0] != null ? row[0].toString() : "",
                        row[1] != null ? ((Number) row[1]).doubleValue() : 0.0,
                        row[2] != null ? ((Number) row[2]).doubleValue() : 0.0
                ))
                .toList();
    }

    public List<EnergyModel> filterData(Integer year, String region, String type) {
        return energyRepository.filterData(year, region, type);
    }

    public List<EnergyModel> getDataByType(String type) {
        return energyRepository.findByEnergyType(type);
    }

    public List<String> getAllRegions() {
        return energyRepository.getAllRegions();
    }

    public List<Integer> getAllYears() {
        return energyRepository.getAllYears();
    }
}