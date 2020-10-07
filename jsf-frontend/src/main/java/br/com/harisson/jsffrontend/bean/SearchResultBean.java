package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.persistence.request.VehicleRequest;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.ExternalContext;
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
    private final ExternalContext externalContext;
    private boolean isFound;

    @Inject
    public SearchResultBean(VehicleRequest vehicleRequest, ExternalContext externalContext) {
        this.vehicleRequest = vehicleRequest;
        this.externalContext = externalContext;
    }

    public void init() {
        listOfSearchedVehiclesOrVehiclesInStock(getParameterString());
    }

    public String vehicleDetails(){
        return "";
    }

    public String deleteVehicle(){
        vehicleRequest.deleteVehicleById(selectedVehicle.getId());
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
}
