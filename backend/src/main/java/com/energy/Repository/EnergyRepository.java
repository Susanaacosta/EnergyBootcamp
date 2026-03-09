package com.energy.Repository;

import com.energy.Model.EnergyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnergyRepository extends JpaRepository<EnergyModel, Long> {

    // Producción total por tipo de energía
    @Query("SELECT e.energyType, SUM(e.production) FROM EnergyModel e GROUP BY e.energyType")
    List<Object[]> getTotalProductionByType();

    //  Top 5 regiones con mayor consumo
    @Query("SELECT e.region, SUM(e.consumption) FROM EnergyModel e GROUP BY e.region ORDER BY SUM(e.consumption) DESC")
    List<Object[]> getTopConsumingRegions();

    // Promedio de capacidad por año
    @Query("SELECT e.yearData, AVG(e.capacity) FROM EnergyModel e GROUP BY e.yearData ORDER BY e.yearData")
    List<Object[]> getAverageCapacityByYear();

    // Relación producción vs consumo por región
    @Query("SELECT e.region, SUM(e.production), SUM(e.consumption) FROM EnergyModel e GROUP BY e.region")
    List<Object[]> getProductionVsConsumption();

    // Listar datos filtrados por tipo (para las tablas)
    List<EnergyModel> findByEnergyType(String type);
}