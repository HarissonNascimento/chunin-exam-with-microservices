package br.com.harisson.springbackend.endpoint.service;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.core.repository.BuyerRepository;
import br.com.harisson.springbackend.endpoint.util.BuyerUtil;
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
public class BuyerService {
    private final BuyerRepository buyerRepository;
    private final BuyerUtil buyerUtil;

    public Iterable<Buyer> listAllBuyers(Sort sort){
        log.info("Listing all buyers");
        return buyerRepository.findAll(sort);
    }

    public List<Buyer> listNonContactedBuyers(){
        log.info("Listing non-contacted buyers");
        return buyerRepository.listNonContactedBuyers();
    }

    public List<Buyer> listContactedBuyers(){
        log.info("Listing contacted buyers");
        return buyerRepository.listContactedBuyers();
    }

    public List<Buyer> findBuyersByVehicleId(Long vehicleId){
        log.info("Listing buyers by vehicle id");
        return buyerRepository.findBuyersByVehicleId(vehicleId);
    }

    public Buyer findById(Long id){
        log.info("Find buyer by id");
        return buyerRepository.findBuyerById(id);
    }

    @Transactional
    public Buyer saveBuyer(Buyer buyer){
        log.info("Saving buyer in database");
        return buyerRepository.save(buyer);
    }

    public void deleteBuyerById(Long id){
        log.info("Deleting buyer by id");
        buyerRepository.delete(buyerUtil.findBuyerOrThrowNotFound(id, buyerRepository));
    }

    public void updateBuyer(Buyer buyer){
        log.info("Updating buyer");
        buyerRepository.save(buyer);
    }

}
