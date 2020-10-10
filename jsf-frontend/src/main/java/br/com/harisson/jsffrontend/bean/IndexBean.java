package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.persistence.request.VehicleRequest;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static br.com.harisson.core.model.enums.VehicleType.*;
import static java.util.stream.Collectors.toList;

@Getter
@Setter
@Named
@ViewScoped
public class IndexBean implements Serializable {
    private final VehicleRequest vehicleRequest;
    private final ExternalContext externalContext;
    private List<Vehicle> vehicleList;
    private Vehicle selectedVehicle;

    @Inject
    public IndexBean(VehicleRequest vehicleRequest, ExternalContext externalContext) {
        this.vehicleRequest = vehicleRequest;
        this.externalContext = externalContext;
    }

    public void init() {
        vehicleList = vehicleRequest.listVehiclesInStock();
    }

    public String vehicleDetails() {
        Flash flash = externalContext.getFlash();
        flash.put("vehicle", selectedVehicle);
        return "vehicledetails.xhtml?faces-redirect=true";
    }

    public List<Vehicle> getListCarsAndUltilitaries() {
        return getVehicleListByType(vehicleList, CARS_AND_ULTILITARIES.getVehicleType());
    }

    public List<Vehicle> getListMotorcyclesAndQuadricycles() {
        return getVehicleListByType(vehicleList, MOTORCYCLES_AND_QUADRICYCLES.getVehicleType());
    }

    public List<Vehicle> getListTruck() {
        return getVehicleListByType(vehicleList, TRUCK.getVehicleType());
    }

    public List<Vehicle> getTrailerAndBodywork() {
        return getVehicleListByType(vehicleList, TRAILER_AND_BODYWORK.getVehicleType());
    }

    public List<Vehicle> getBusesAndVans() {
        return getVehicleListByType(vehicleList, BUSES_AND_VANS.getVehicleType());
    }

    public List<Vehicle> getNautical() {
        return getVehicleListByType(vehicleList, NAUTICAL.getVehicleType());
    }

    public List<Vehicle> getOtherTypes() {
        return getVehicleListByType(vehicleList, OTHERS.getVehicleType());
    }

    public List<Vehicle> announcementVehicles() {
        return vehicleList.stream()
                .limit(9)
                .collect(toList());
    }

    private List<Vehicle> getVehicleListByType(List<Vehicle> list, String type) {
        return list.stream()
                .filter(v -> v.getVehicleType().equals(type))
                .collect(toList());
    }
}
