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
import java.io.File;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Named
@ViewScoped
public class VehicleDetailsBean implements Serializable {
    private Vehicle selectedVehicle;
    private Buyer buyer;
    private List<Buyer> buyersList;
    private transient boolean isEditing;
    private final BuyerRequest buyerRequest;
    private final VehicleRequest vehicleRequest;
    private final ExternalContext externalContext;
    private final ImagesUtil imagesUtil;

    @Inject
    public VehicleDetailsBean(BuyerRequest buyerRequest, VehicleRequest vehicleRequest, ExternalContext externalContext, ImagesUtil imagesUtil) {
        this.buyerRequest = buyerRequest;
        this.vehicleRequest = vehicleRequest;
        this.externalContext = externalContext;
        this.imagesUtil = imagesUtil;
    }

    public void init() {
        Flash flash = externalContext.getFlash();
        selectedVehicle = (Vehicle) flash.get("vehicle");
        buyersList = buyerRequest.findBuyersByVehicleId(selectedVehicle.getId());
        buyer = new Buyer();
    }

    public void editVehicle() {
        isEditing = true;
    }

    public void updateVehicle() {
        setBuyerForVehicle();
        vehicleRequest.updateVehicle(selectedVehicle);
        isEditing = false;
    }

    public List<String> listNameOfImages() {
        File file = new File(imagesUtil.getVehicleImagesDirectoryName(selectedVehicle, externalContext));
        return imagesUtil.listNamesOfImagesInDirectory(file);
    }

    public String buyVehicle() {
        buyer.setVehicle(selectedVehicle);
        Flash flash = externalContext.getFlash();
        flash.put("buyer", buyer);
        return "registernewbuyer.xhtml?faces-redirect=true";
    }

    private void setBuyerForVehicle() {
        if (buyer.getId() != null) {
            selectedVehicle.setBuyerId(buyer.getId());
        }
    }
}
