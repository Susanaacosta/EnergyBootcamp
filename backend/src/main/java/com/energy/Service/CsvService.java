package com.energy.Service;

import com.energy.Model.EnergyModel;
import com.energy.Repository.EnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CsvService {

    @Autowired
    private EnergyRepository energyRepository;

    @Transactional
    public void processLine(String[] data, String type) {
        String region = data[0];
        String code = data[1];
        Integer year = Integer.parseInt(data[2]);

        // Buscamos si ya existe el registro
        EnergyModel entity = energyRepository.findByRegionAndYearData(region, year);

        if (entity == null) {
            entity = new EnergyModel();
            entity.setRegion(region);
            entity.setCode(code);
            entity.setYearData(year);
        }

        double val1 = data.length > 3 ? Double.parseDouble(data[3]) : 0.0;

        // Comparación de strings correcta en Java
        if ("production".equals(type)) {
            entity.setWindTwh(val1);
            entity.setHydroTwh(data.length > 4 ? Double.parseDouble(data[4]) : 0.0);
            entity.setSolarTwh(data.length > 5 ? Double.parseDouble(data[5]) : 0.0);
            entity.setOtherRenewablesTwh(data.length > 6 ? Double.parseDouble(data[6]) : 0.0);
        } else if ("percentage".equals(type)) {
            entity.setRenewablesPercentage(val1);
        } else if ("capacity".equals(type)) {
            entity.setSolarCapacity(val1);
        }

        energyRepository.save(entity);
    }
}