package br.com.harisson.core.repository;

import br.com.harisson.core.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

    Vehicle findVehicleById(Long id);

    @Query("select v from Vehicle v where v.model like %?1%")
    List<Vehicle> listVehiclesByModel(String model);

    @Query("select v from Vehicle v where v.isSold = false")
    List<Vehicle> listVehiclesInStock();

    @Query("select v from Vehicle v where v.isSold = true")
    List<Vehicle> listSoldVehicles();

}
