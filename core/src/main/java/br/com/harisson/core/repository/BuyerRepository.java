package br.com.harisson.core.repository;

import br.com.harisson.core.model.Buyer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BuyerRepository extends PagingAndSortingRepository<Buyer, Long> {

    Buyer findBuyerById(Long id);

    @Query("select b from Buyer b where b.vehicle.id = ?1")
    List<Buyer> findBuyersByVehicleId(Long vehicleId);

    @Query("select b from Buyer b where b.isContacted = false")
    List<Buyer> listNonContactedBuyers();

    @Query("select b from Buyer b where b.isContacted = true")
    List<Buyer> listContactedBuyers();

}
