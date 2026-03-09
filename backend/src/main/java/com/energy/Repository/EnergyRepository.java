package com.energy.Repository;

import com.energy.Model.EnergyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyRepository extends JpaRepository<EnergyModel, Long> {

    @Query("""
        SELECT e.energyType, SUM(COALESCE(e.production, 0))
        FROM EnergyModel e
        WHERE (:year IS NULL OR e.yearData = :year)
          AND (:region IS NULL OR LOWER(e.region) LIKE LOWER(CONCAT('%', :region, '%')))
          AND (:type IS NULL OR UPPER(e.energyType) = UPPER(:type))
        GROUP BY e.energyType
        ORDER BY SUM(COALESCE(e.production, 0)) DESC
    """)
    List<Object[]> getProductionByFilters(@Param("year") Integer year,
                                          @Param("region") String region,
                                          @Param("type") String type);

    @Query("""
        SELECT e.region, SUM(COALESCE(e.consumption, 0))
        FROM EnergyModel e
        WHERE (:year IS NULL OR e.yearData = :year)
          AND (:type IS NULL OR UPPER(e.energyType) = UPPER(:type))
        GROUP BY e.region
        ORDER BY SUM(COALESCE(e.consumption, 0)) DESC
    """)
    List<Object[]> getConsumptionByFilters(@Param("year") Integer year,
                                           @Param("type") String type);

    @Query("""
        SELECT e.yearData, AVG(COALESCE(e.capacity, 0))
        FROM EnergyModel e
        WHERE (:region IS NULL OR LOWER(e.region) LIKE LOWER(CONCAT('%', :region, '%')))
          AND (:type IS NULL OR UPPER(e.energyType) = UPPER(:type))
        GROUP BY e.yearData
        ORDER BY e.yearData
    """)
    List<Object[]> getCapacityEvolutionByFilters(@Param("region") String region,
                                                 @Param("type") String type);

    @Query("""
        SELECT e.region, SUM(COALESCE(e.production, 0)), SUM(COALESCE(e.consumption, 0))
        FROM EnergyModel e
        WHERE (:year IS NULL OR e.yearData = :year)
          AND (:type IS NULL OR UPPER(e.energyType) = UPPER(:type))
        GROUP BY e.region
        ORDER BY e.region
    """)
    List<Object[]> getRegionalCompareByFilters(@Param("year") Integer year,
                                               @Param("type") String type);

    @Query("""
        SELECT e
        FROM EnergyModel e
        WHERE (:year IS NULL OR e.yearData = :year)
          AND (:region IS NULL OR LOWER(e.region) LIKE LOWER(CONCAT('%', :region, '%')))
          AND (:type IS NULL OR UPPER(e.energyType) = UPPER(:type))
        ORDER BY e.yearData DESC, e.region ASC
    """)
    List<EnergyModel> filterData(@Param("year") Integer year,
                                 @Param("region") String region,
                                 @Param("type") String type);

    List<EnergyModel> findByEnergyType(String type);

    @Query("SELECT DISTINCT e.region FROM EnergyModel e ORDER BY e.region")
    List<String> getAllRegions();

    @Query("SELECT DISTINCT e.yearData FROM EnergyModel e ORDER BY e.yearData")
    List<Integer> getAllYears();
}