package com.energy.Service;

import com.energy.Model.EnergyModel;
import com.energy.Repository.EnergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class EnergyService {

    @Autowired
    private EnergyRepository repository;

public void cargarTodo(String fileProd, String fileShare, String fileCap) {
    System.out.println("Iniciando proceso de carga...");
    
    // Ahora usamos las variables que recibimos desde BackendApplication
    cargarArchivoProduccion(fileProd);
    cargarArchivoProduccion(fileShare);
    cargarArchivoProduccion(fileCap);
    
    System.out.println("Carga finalizada");
}

    private void cargarArchivoProduccion(String nombreArchivo) {
        try {
            // Usamos ClassPathResource pa' encontrar o leer los archicos cvs en resources
            ClassPathResource resource = new ClassPathResource(nombreArchivo);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Salta el encabezado
                }

                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    EnergyModel model = new EnergyModel();
                    model.setEntity(datos[0].trim());
                    model.setCode(datos[1].trim());
                    model.setDataYear(Integer.parseInt(datos[2].trim()));
                
                    model.setHydroTwh(parseDoubleSafe(datos[3]));
                    //setea el resto de campos Solar, Wind
                    
                    repository.save(model);
                }
            }
            System.out.println("Cargado con exito: " + nombreArchivo);
            reader.close();

        } catch (Exception e) {
            System.err.println("Error cargando" + nombreArchivo + ": " + e.getMessage());
        }
    }

    private Double parseDoubleSafe(String valor) {
        try {
            return (valor == null || valor.trim().isEmpty()) ? 0.0 : Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}