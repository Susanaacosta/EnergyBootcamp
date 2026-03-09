package com.energy.Model;

import jakarta.persistence.*;
import lombok.*;
//Nombre de la tablas y columnas de la BD
@Entity
@Table(name = "energy_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String energyType; // Solar, Eólica, etc.
    private String region;
    private Double production;
    private Double consumption;
    private Double capacity;
    private Integer yearData;
}