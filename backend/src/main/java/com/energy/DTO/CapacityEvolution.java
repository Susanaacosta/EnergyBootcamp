package com.energy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para evolución de capacidad por año.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapacityEvolution {

    private Integer year;
    private Double averageCapacity;
}