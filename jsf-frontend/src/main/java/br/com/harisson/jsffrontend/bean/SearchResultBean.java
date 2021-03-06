package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.persistence.request.BuyerRequest;
import br.com.harisson.jsffrontend.persistence.request.VehicleRequest;
import br.com.harisson.jsffrontend.util.ImagesUtil;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Named
@ViewScoped
public class SearchResultBean implements Serializable {
    private List<Vehicle> listVehicles;
    private Vehicle selectedVehicle;
    private final VehicleRequest vehicleRequest;
    private final BuyerRequest buyerRequest;
    private final ExternalContext externalContext;
    private final ImagesUtil imagesUtil;
    private boolean isFound;

    @Inject
    public SearchResultBean(VehicleRequest vehicleRequest, BuyerRequest buyerRequest, ExternalContext externalContext, ImagesUtil imagesUtil) {
        this.vehicleRequest = vehicleRequest;
        this.buyerRequest = buyerRequest;
        this.externalContext = externalContext;
        this.imagesUtil = imagesUtil;
    }

    public void init() {
        listOfSearchedVehiclesOrVehiclesInStock(getParameterString());
    }

    public String vehicleDetails() {
        Flash flash = externalContext.getFlash();
        flash.put("vehicle", selectedVehicle);
        return "vehicledetails.xhtml?faces-redirect=true";
    }

    public String deleteVehicle() {
        deleteAllBuyersForVehicle(selectedVehicle);
        vehicleRequest.deleteVehicleById(selectedVehicle.getId());
        imagesUtil.deleteVehicleImageFolder(selectedVehicle, externalContext);
        return "searchresult.xhtml?faces-redirect=true";
    }

    public String getParameterString() {
        Map<String, String> paramsMap = externalContext.getRequestParameterMap();
        return paramsMap.get("model");
    }

    private void listOfSearchedVehiclesOrVehiclesInStock(String stringSearch) {
        if (stringSearch == null || stringSearch.equals("")) {
            listVehicles = vehicleRequest.listVehiclesInStock();
            return;
        }
        listVehicles = listOfVehiclesFoundOrNot(stringSearch);
    }

    private List<Vehicle> listOfVehiclesFoundOrNot(String model) {
        List<Vehicle> list = vehicleRequest.listVehiclesByModel(model);
        if (list.isEmpty()) {
            isFound = true;
            return vehicleRequest.listVehiclesInStock();
        }
        return list;
    }

    private void deleteAllBuyersForVehicle(Vehicle vehicle) {
        List<Buyer> buyerList = buyerRequest.findBuyersByVehicleId(vehicle.getId());
        if (!buyerList.isEmpty()) {
            for (Buyer b : buyerList) {
                buyerRequest.deleteBuyerById(b.getId());
            }
        }
    }
}
