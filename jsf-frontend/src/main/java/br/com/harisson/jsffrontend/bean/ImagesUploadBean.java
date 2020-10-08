package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.persistence.request.VehicleRequest;
import br.com.harisson.jsffrontend.util.ImagesUtil;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFiles;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;

@Getter
@Setter
@Named
@ViewScoped
public class ImagesUploadBean implements Serializable {
    private Vehicle vehicle;
    private UploadedFiles filesToUpload;
    private final ExternalContext externalContext;
    private final ImagesUtil imagesUtil;
    private final VehicleRequest vehicleRequest;

    @Inject
    public ImagesUploadBean(ExternalContext externalContext, ImagesUtil imagesUtil, VehicleRequest vehicleRequest) {
        this.externalContext = externalContext;
        this.imagesUtil = imagesUtil;
        this.vehicleRequest = vehicleRequest;
    }

    public void init() {
        Flash flash = externalContext.getFlash();
        vehicle = (Vehicle) flash.get("vehicle");
    }

    public String saveVehicle() {
        imagesUtil.setThumbnailName(vehicle);
        vehicleRequest.saveVehicle(vehicle);
        return "searchresult.xhtml?faces-redirect=true";
    }

    public void uploadImages() {
        if (filesToUpload != null) {
            File file = new File(imagesUtil.getVehicleImagesDirectoryName(vehicle, externalContext));
            imagesUtil.uploadImagesToFolder(filesToUpload, file);
        }
    }

    public boolean isButtonDisabled() {
        return imagesUtil.checkFolderContainsOneOrMoreFiles();
    }
}
