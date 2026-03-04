package com.energy.Repository;

import com.energy.Model.EnergyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnergyRepository extends JpaRepository<EnergyModel, Long> {

    // Cambie 'findByEntityAndDataYear' por este para que no de error de nombres
    Optional<EnergyModel> findByEntityAndDataYear(String entity, Integer dataYear);

    // Los Querys manuales usan los nombres de las variables de tu clase
    @Query("SELECT SUM(e.hydroTwh), SUM(e.solarTwh), SUM(e.windTwh) FROM EnergyModel e WHERE e.dataYear = :year")
    List<Object[]> findGlobalProductionByYear(@Param("year") Integer year);

    @Query("SELECT e.entity, e.windTwh FROM EnergyModel e WHERE e.dataYear = :year AND e.code IS NOT NULL ORDER BY e.windTwh DESC")
    List<Object[]> findTop10WindProduction(@Param("year") Integer year);

    @Query("SELECT e.dataYear, e.solarCapacityGw FROM EnergyModel e WHERE e.entity = :country ORDER BY e.dataYear ASC")
    List<Object[]> findSolarCapacityTrend(@Param("country") String country);
}