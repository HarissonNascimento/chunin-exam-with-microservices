package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class VehicleUtil {
    public Vehicle findVehicleOrThrowNotFound(Long id, VehicleRepository vehicleRepository) {
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }
}
