package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Vehicle;
import br.com.harisson.jsffrontend.persistence.request.VehicleRequest;
import br.com.harisson.jsffrontend.util.UploadImagesUtil;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFiles;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private final UploadImagesUtil uploadImagesUtil;
    private final VehicleRequest vehicleRequest;

    @Inject
    public ImagesUploadBean(ExternalContext externalContext, UploadImagesUtil uploadImagesUtil, VehicleRequest vehicleRequest) {
        this.externalContext = externalContext;
        this.uploadImagesUtil = uploadImagesUtil;
        this.vehicleRequest = vehicleRequest;
    }

    public void init(){
        Flash flash = externalContext.getFlash();
        vehicle = (Vehicle) flash.get("vehicle");
    }

    public String saveVehicle(){
        vehicleRequest.saveVehicle(vehicle);
        return "";
    }

    public void uploadImages(){
        if (filesToUpload != null){
            File file = new File(uploadImagesUtil.getVehicleImagesDirectoryName(vehicle, externalContext));
            uploadImagesUtil.uploadImagesToFolder(filesToUpload, file);
        }
    }

    public boolean isButtonDisabled(){
        return uploadImagesUtil.checkFolderContainsOneOrMoreFiles();
    }
}
