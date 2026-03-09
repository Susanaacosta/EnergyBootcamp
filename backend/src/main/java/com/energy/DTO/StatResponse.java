package com.energy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico para respuestas estadísticas simples.
 * Se usa para devolver un label y un value.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatResponse {

    private String label;
    private Double value;
}