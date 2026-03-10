package com.energy.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "energy_data")
@Data // @Data ya incluye @Getter, @Setter, @ToString y @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EnergyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;
    private String code;

    @Column(name = "year_data")
    private Integer yearData;

    @Column(name = "wind_twh")
    private Double windTwh;

    @Column(name = "hydro_twh")
    private Double hydroTwh;

    @Column(name = "solar_twh")
    private Double solarTwh;

    @Column(name = "other_renewables_twh")
    private Double otherRenewablesTwh;

    @Column(name = "renewables_percentage")
    private Double renewablesPercentage;

    @Column(name = "solar_capacity")
    private Double solarCapacity;
}