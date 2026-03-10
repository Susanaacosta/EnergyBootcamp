package com.energy.Repository;

import com.energy.Model.EnergyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnergyRepository extends JpaRepository<EnergyModel, Long> {

    EnergyModel findByRegionAndYearData(String region, Integer yearData);

    // #1. Producción total de energía renovable por tipo de fuente en un año específico, agrupada por regiones.
    @Query("SELECT e.region, e.solarTwh, e.windTwh, e.hydroTwh, e.otherRenewablesTwh " +
           "FROM EnergyModel e WHERE e.yearData = :year " +
           "AND (e.code IS NULL OR e.code = '' OR LENGTH(e.code) > 3) " + 
           "AND e.region NOT IN ('World', 'High-income countries', 'Low-income countries', 'Upper-middle-income countries', 'Lower-middle-income countries')")
    List<Object[]> findProductionBySourceAndYear(@Param("year") int year);

    // #2. Porcentaje de energía renovable en el consumo eléctrico total de cada región.
    @Query("SELECT e.region, e.renewablesPercentage FROM EnergyModel e " +
           "WHERE e.yearData = :year " +
           "AND (e.code IS NULL OR e.code = '' OR LENGTH(e.code) > 3) " +
           "AND e.region NOT IN ('World', 'High-income countries', 'Low-income countries') " +
           "AND e.renewablesPercentage IS NOT NULL")
    List<Object[]> findRenewablePercentageByYear(@Param("year") int year);

    // #3. Tendencia de la capacidad instalada de energía solar a lo largo de los años.
    @Query("SELECT e.yearData, e.solarCapacity FROM EnergyModel e " +
           "WHERE e.region = 'World' AND e.solarCapacity IS NOT NULL " +
           "ORDER BY e.yearData ASC")
    List<Object[]> findGlobalSolarTrend();

    // #4. Los 10 países con mayor producción de energía eólica en un año específico.
    @Query(value = "SELECT region, wind_twh FROM energy_data " +
                   "WHERE year_data = :year AND LENGTH(code) = 3 " +
                   "AND wind_twh IS NOT NULL " +
                   "ORDER BY wind_twh DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10WindByYear(@Param("year") int year);

    // #5. Fuentes de energía y su participación en el consumo eléctrico total a nivel global.
    @Query("SELECT SUM(e.solarTwh), SUM(e.windTwh), SUM(e.hydroTwh), SUM(e.otherRenewablesTwh) " +
           "FROM EnergyModel e WHERE e.yearData = :year AND LENGTH(e.code) = 3")
    List<Object[]> findGlobalParticipationByYear(@Param("year") int year);
}

