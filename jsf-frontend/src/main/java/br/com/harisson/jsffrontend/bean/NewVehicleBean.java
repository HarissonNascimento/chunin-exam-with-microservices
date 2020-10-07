package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Named
@ViewScoped
public class NewVehicleBean implements Serializable {
    private Vehicle vehicle;
    private final ExternalContext externalContext;

    @Inject
    public NewVehicleBean(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public void init(){
        vehicle = new Vehicle();
    }

    public String nextPage(){
        vehicle.setImagesFolderDirectory(generateImagesFolderName());
        Flash flash = externalContext.getFlash();
        flash.put("vehicle", vehicle);
        return "imagesuploadadmin.xhtml?faces-redirect=true";
    }

    private String generateImagesFolderName(){
        return UUID.randomUUID().toString();
    }
}
