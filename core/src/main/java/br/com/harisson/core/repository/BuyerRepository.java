package br.com.harisson.core.repository;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BuyerRepository extends PagingAndSortingRepository<Buyer, Long> {

    Buyer findBuyerById(Long id);

    List<Buyer> findBuyersByVehicle(Vehicle vehicle);

    @Query("select b from Buyer b where b.name like %?1%")
    List<Buyer> findBuyersByName(String name);

}
