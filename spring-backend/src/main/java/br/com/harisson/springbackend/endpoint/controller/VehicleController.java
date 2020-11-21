package br.com.harisson.springbackend.endpoint.controller;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.springbackend.endpoint.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("v1/vehicle")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoint to manage vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping(path = "/listAllVehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all available vehicles", response = Vehicle[].class)
    public ResponseEntity<Iterable<Vehicle>> listAllVehicles(Sort sort) {
        return ResponseEntity.ok(vehicleService.listAllVehicles(sort));
    }

    @GetMapping(path = "/listVehiclesInStock", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all available vehicles in stock", response = Vehicle[].class)
    public ResponseEntity<List<Vehicle>> listVehiclesInStock() {
        return ResponseEntity.ok(vehicleService.listVehiclesInStock());
    }

    @GetMapping(path = "/listSoldVehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all available sold vehicles", response = Vehicle[].class)
    public ResponseEntity<List<Vehicle>> listSoldVehicles() {
        return ResponseEntity.ok(vehicleService.listSoldVehicles());
    }

    @GetMapping(path = "/listVehiclesByModel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List vehicles by model", response = Vehicle[].class)
    public ResponseEntity<List<Vehicle>> listVehiclesByModel(@RequestParam(value = "model", defaultValue = "") String model) {
        return ResponseEntity.ok(vehicleService.listVehiclesByModel(model));
    }

    @GetMapping(path = "/findVehicleById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search vehicle by id", response = Vehicle.class)
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
    }

    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
