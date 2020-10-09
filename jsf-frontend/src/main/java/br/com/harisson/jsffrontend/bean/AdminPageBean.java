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

@Getter
@Setter
@Named
@ViewScoped
public class AdminPageBean implements Serializable {
    private final VehicleRequest vehicleRequest;
    private final BuyerRequest buyerRequest;
    private final ExternalContext externalContext;
    private final ImagesUtil imagesUtil;
    private Vehicle selectedVehicle;
    private Buyer selectedBuyer;

    @Inject
    public AdminPageBean(VehicleRequest vehicleRequest, BuyerRequest buyerRequest, ExternalContext externalContext, ImagesUtil imagesUtil) {
        this.vehicleRequest = vehicleRequest;
        this.buyerRequest = buyerRequest;
        this.externalContext = externalContext;
        this.imagesUtil = imagesUtil;
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
        return "adminpage.xhtml?faces-redirect=true";
    }

    public List<Vehicle> vehiclesInStock() {
        return vehicleRequest.listVehiclesInStock();
    }

    public List<Vehicle> soldVehicles() {
        return vehicleRequest.listSoldVehicles();
    }

    public List<Buyer> contactedBuyers() {
        return buyerRequest.listContactedBuyers();
    }

    public List<Buyer> nonContactedBuyers() {
        return buyerRequest.listNonContactedBuyers();
    }

    public String markBuyerAsContacted() {
        return updateSelectedBuyerAfterContacting(selectedBuyer);
    }

    private String updateSelectedBuyerAfterContacting(Buyer buyer) {
        if (buyer == null) {
            return "";
        }
        buyer.setContacted(true);
        buyerRequest.updateBuyer(buyer);
        return "adminpage.xhtml?faces-redirect=true";
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
