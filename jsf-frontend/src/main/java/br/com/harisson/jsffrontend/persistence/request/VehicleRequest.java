package br.com.harisson.jsffrontend.persistence.request;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.annotation.ExceptionUnauthorized;
import br.com.harisson.jsffrontend.custom.CustomRestTemplate;
import br.com.harisson.jsffrontend.util.JsonUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static br.com.harisson.jsffrontend.util.APIUtil.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.*;

public class VehicleRequest implements Serializable {
    private final CustomRestTemplate restTemplate;
    private final JsonUtil jsonUtil;

    @Inject
    public VehicleRequest(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jsonUtil = jsonUtil;
    }

    public List<Vehicle> listAllVehicles() {
        return returnOrderedList(listFromBodyResponseEntityGetRequestNoParameter(URL_VEHICLE_LIST_ALL.getUrl()));
    }

    public List<Vehicle> listVehiclesInStock() {
        return returnOrderedList(listFromBodyResponseEntityGetRequestNoParameter(URL_VEHICLE_LIST_IN_STOCK.getUrl()));
    }

    public List<Vehicle> listSoldVehicles() {
        return listFromBodyResponseEntityGetRequestNoParameter(URL_VEHICLE_LIST_SOLD.getUrl());
    }

    public List<Vehicle> listVehiclesByModel(String model) {
        return returnOrderedList(listFromBodyResponseEntityGetRequestParameter(URL_VEHICLE_LIST_BY_MODEL.getUrl(), model));
    }

    public Vehicle findById(Long id) {
        ResponseEntity<Vehicle> exchange = restTemplate.exchange(URL_VEHICLE_FIND_BY_ID.getUrl(),
                GET, new HttpEntity<>(URL_VEHICLE_FIND_BY_ID.getUrl(), jsonUtil.createJsonHeader()),
                Vehicle.class, id);
        return exchange.getBody();
    }

    @ExceptionUnauthorized
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleFromBodyResponseEntityRequest(URL_VEHICLE_POST.getUrl(), POST, vehicle);
    }

    @ExceptionUnauthorized
    public void deleteVehicleById(Long id) {
        restTemplate.exchange(URL_VEHICLE_DELETE_BY_ID.getUrl(),
                DELETE,
                jsonUtil.tokenizedHttpEntityHeader(URL_VEHICLE_DELETE_BY_ID.getUrl()),
                Vehicle.class, id);
    }

    @ExceptionUnauthorized
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleFromBodyResponseEntityRequest(URL_VEHICLE_PUT.getUrl(), PUT, vehicle);
    }

    @ExceptionUnauthorized
    private Vehicle vehicleFromBodyResponseEntityRequest(String url, HttpMethod method, Vehicle vehicle) {
        return restTemplate.exchange(url,
                method,
                jsonUtil.tokenizedHttpEntityHeader(vehicle),
                Vehicle.class).getBody();
    }

    private List<Vehicle> listFromBodyResponseEntityGetRequestNoParameter(String url) {
        ResponseEntity<List<Vehicle>> exchange = restTemplate.exchange(url,
                GET, new HttpEntity<>(url, jsonUtil.createJsonHeader()),
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    private List<Vehicle> listFromBodyResponseEntityGetRequestParameter(String url, String parameter) {
        UriComponents urlParametrized = UriComponentsBuilder.fromUriString(url).queryParam("model", parameter).build();
        ResponseEntity<List<Vehicle>> exchange = restTemplate.exchange(urlParametrized.toUriString(),
                GET, new HttpEntity<>(urlParametrized.toUriString(), jsonUtil.createJsonHeader()),
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    private List<Vehicle> returnOrderedList(List<Vehicle> list) {
        if (!list.isEmpty()) {
            return list.stream()
                    .sorted(comparing(Vehicle::getId).reversed())
                    .collect(toList());
        }
        return new ArrayList<>();

    }
}
