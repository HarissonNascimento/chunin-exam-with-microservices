package br.com.harisson.jsffrontend.bean;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.jsffrontend.persistence.request.BuyerRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FlowEvent;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import static java.util.concurrent.TimeUnit.SECONDS;

@Getter
@Setter
@Named
@ViewScoped
public class RegisterNewBuyerBean implements Serializable {
    private Buyer newBuyer;
    private final BuyerRequest buyerRequest;
    private final ExternalContext externalContext;

    @Inject
    public RegisterNewBuyerBean(BuyerRequest buyerRequest, ExternalContext externalContext) {
        this.buyerRequest = buyerRequest;
        this.externalContext = externalContext;
    }

    public void init() {
        Flash flash = externalContext.getFlash();
        newBuyer = (Buyer) flash.get("buyer");
    }

    public String saveBuyer() {
        buyerRequest.saveBuyer(newBuyer);
        return waitingTimeAndReturnsToIndex();
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    private String waitingTimeAndReturnsToIndex() {
        try {
            SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index.xhtml?faces-redirect=true";
    }

}
