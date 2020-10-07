package br.com.harisson.jsffrontend.bean;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Getter
@Setter
@Named
@RequestScoped
public class SearchBean implements Serializable {
    private String model;

    public String searchByModel() {
        return "searchresult.xhtml?faces-redirect=true&model=".concat(model);
    }
}
