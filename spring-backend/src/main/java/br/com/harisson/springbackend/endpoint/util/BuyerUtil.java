package br.com.harisson.springbackend.endpoint.util;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.repository.BuyerRepository;
import br.com.harisson.springbackend.endpoint.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BuyerUtil {
    public Buyer findBuyerOrThrowNotFound(Long id, BuyerRepository buyerRepository) {
        return buyerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
    }
}
