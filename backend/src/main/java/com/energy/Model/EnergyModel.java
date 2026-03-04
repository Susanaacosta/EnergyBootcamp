package com.energy.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "energy_data")
public class EnergyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity")
    private String entity;

    @Column(name = "code")
    private String code;

    @Column(name = "data_year")
    private Integer dataYear;

    // -Datos de Producción - 1er Archivo---
    @Column(name = "hydro_twh")
    private Double hydroTwh;

    @Column(name = "solar_twh")
    private Double solarTwh;

    @Column(name = "wind_twh")
    private Double windTwh;

    @Column(name = "other_renewables_twh")
    private Double otherRenewablesTwh;

    @Column(name = "renewables_share_percent")
    private Double renewablesSharePercent;

    // --- Datos de Mediciones/Capacidad -2Do cvs ---
    @Column(name = "solar_capacity_gw")
    private Double solarCapacityGw;

    // Constructor vacío obligatorio para JPA
    public EnergyModel() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDataYear() {
        return dataYear;
    }

    public void setDataYear(Integer dataYear) {
        this.dataYear = dataYear;
    }

    public Double getHydroTwh() {
        return hydroTwh;
    }

    public void setHydroTwh(Double hydroTwh) {
        this.hydroTwh = hydroTwh;
    }

    public Double getSolarTwh() {
        return solarTwh;
    }

    public void setSolarTwh(Double solarTwh) {
        this.solarTwh = solarTwh;
    }

    public Double getWindTwh() {
        return windTwh;
    }

    public void setWindTwh(Double windTwh) {
        this.windTwh = windTwh;
    }

    public Double getOtherRenewablesTwh() {
        return otherRenewablesTwh;
    }

    public void setOtherRenewablesTwh(Double otherRenewablesTwh) {
        this.otherRenewablesTwh = otherRenewablesTwh;
    }

    public Double getRenewablesSharePercent() {
        return renewablesSharePercent;
    }

    public void setRenewablesSharePercent(Double renewablesSharePercent) {
        this.renewablesSharePercent = renewablesSharePercent;
    }

    public Double getSolarCapacityGw() {
        return solarCapacityGw;
    }

    public void setSolarCapacityGw(Double solarCapacityGw) {
        this.solarCapacityGw = solarCapacityGw;
    }
}