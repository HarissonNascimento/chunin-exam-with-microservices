package br.com.harisson.springbackend.endpoint.service;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.VehicleRepository;
import br.com.harisson.springbackend.endpoint.util.VehicleUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleUtil vehicleUtil;

    public Iterable<Vehicle> listAllVehicles(Sort sort){
        log.info("Listing all vehicles");
        return vehicleRepository.findAll(sort);
    }

    public List<Vehicle> listVehiclesInStock(){
        log.info("Listing vehicles in stock");
        return vehicleRepository.listVehiclesInStock();
    }

    public List<Vehicle> listSoldVehicles(){
        log.info("Listing sold vehicles");
        return vehicleRepository.listSoldVehicles();
    }

    public List<Vehicle> listVehiclesByModel(String model){
        log.info("Listing vehicles by model");
        return vehicleRepository.listVehiclesByModel(model);
    }

    public Vehicle findById(Long id){
        log.info("Find vehicle by id");
        return vehicleRepository.findVehicleById(id);
    }

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle){
        log.info("Saving vehicle in database");
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Long id){
        log.info("Deleting vehicle by id");
        vehicleRepository.delete(vehicleUtil.findVehicleOrThrowNotFound(id, vehicleRepository));
    }

    public void updateVehicle(Vehicle vehicle){
        log.info("Updating vehicle");
        vehicleRepository.save(vehicle);
    }
}
