package br.com.harisson.springbackend.endpoint.controller;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.springbackend.endpoint.service.BuyerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/buyer")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuyerController {
    private final BuyerService buyerService;

    @GetMapping(path = "/listAllBuyers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Buyer>> listAllBuyers(Sort sort) {
        return ResponseEntity.ok(buyerService.listAllBuyers(sort));
    }

    @GetMapping(path = "/listNonContactedBuyers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Buyer>> listNonContactedBuyers() {
        return ResponseEntity.ok(buyerService.listNonContactedBuyers());
    }

    @GetMapping(path = "/listContactedBuyers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Buyer>> listContactedBuyers() {
        return ResponseEntity.ok(buyerService.listContactedBuyers());
    }

    @GetMapping(path = "/findBuyersByVehicle", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Buyer>> findBuyersByVehicle(@RequestBody @Valid Vehicle vehicle) {
        return ResponseEntity.ok(buyerService.findBuyersByVehicle(vehicle));
    }

    @GetMapping(path = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Buyer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(buyerService.findById(id));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Buyer> saveBuyer(@RequestBody @Valid Buyer buyer) {
        return ResponseEntity.ok(buyerService.saveBuyer(buyer));
    }

    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity<Void> deleteBuyerById(@PathVariable Long id) {
        buyerService.deleteBuyerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBuyer(@RequestBody Buyer buyer) {
        buyerService.updateBuyer(buyer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
