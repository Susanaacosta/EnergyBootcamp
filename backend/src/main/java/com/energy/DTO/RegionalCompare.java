package com.energy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para comparar producción vs consumo por región.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionalCompare {

    private String region;
    private Double production;
    private Double consumption;
}