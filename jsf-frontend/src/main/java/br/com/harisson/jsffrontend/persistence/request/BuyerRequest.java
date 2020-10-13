package br.com.harisson.jsffrontend.persistence.request;

import br.com.harisson.core.model.Buyer;
import br.com.harisson.jsffrontend.annotation.ExceptionUnauthorized;
import br.com.harisson.jsffrontend.custom.CustomRestTemplate;
import br.com.harisson.jsffrontend.util.JsonUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static br.com.harisson.jsffrontend.util.APIUtil.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.*;

public class BuyerRequest implements Serializable {
    private final CustomRestTemplate restTemplate;
    private final JsonUtil jsonUtil;

    @Inject
    public BuyerRequest(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jsonUtil = jsonUtil;
    }

    public List<Buyer> listAllBuyers() {
        return returnOrderedList(listFromBodyResponseEntityGetRequestNoParameter(URL_BUYER_LIST_ALL.getUrl()));
    }

    public List<Buyer> listNonContactedBuyers() {
        return returnOrderedList(listFromBodyResponseEntityGetRequestNoParameter(URL_BUYER_LIST_NON_CONTACTED.getUrl()));
    }

    public List<Buyer> listContactedBuyers() {
        return returnOrderedList(listFromBodyResponseEntityGetRequestNoParameter(URL_BUYER_LIST_CONTACTED.getUrl()));
    }

    public List<Buyer> findBuyersByVehicleId(Long vehicleId) {
        return returnOrderedList(listFromBodyResponseEntityGetRequestParameter(URL_BUYER_FIND_BY_VEHICLE_ID.getUrl(), vehicleId));
    }

    public Buyer findById(Long id) {
        ResponseEntity<Buyer> exchange = restTemplate.exchange(URL_BUYER_FIND_BY_ID.getUrl(),
                GET, new HttpEntity<>(URL_BUYER_FIND_BY_ID.getUrl(), jsonUtil.createJsonHeader()),
                Buyer.class, id);
        return exchange.getBody();
    }

    public Buyer saveBuyer(Buyer buyer) {
        return restTemplate.exchange(URL_BUYER_POST.getUrl(),
                POST,
                new HttpEntity<>(buyer, jsonUtil.createJsonHeader()),
                Buyer.class).getBody();
    }

    @ExceptionUnauthorized
    public void deleteBuyerById(Long id) {
        restTemplate.exchange(URL_BUYER_DELETE_BY_ID.getUrl(),
                DELETE,
                jsonUtil.tokenizedHttpEntityHeader(URL_BUYER_DELETE_BY_ID.getUrl()),
                Buyer.class, id);
    }

    @ExceptionUnauthorized
    public Buyer updateBuyer(Buyer buyer) {
        return buyerFromBodyResponseEntityRequest(URL_BUYER_PUT.getUrl(), PUT, buyer);
    }

    @ExceptionUnauthorized
    private Buyer buyerFromBodyResponseEntityRequest(String url, HttpMethod method, Buyer buyer) {
        return restTemplate.exchange(url,
                method,
                jsonUtil.tokenizedHttpEntityHeader(buyer),
                Buyer.class).getBody();
    }

    private List<Buyer> listFromBodyResponseEntityGetRequestNoParameter(String url) {
        ResponseEntity<List<Buyer>> exchange = restTemplate.exchange(url,
                GET, new HttpEntity<>(url, jsonUtil.createJsonHeader()),
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody();
    }

    private List<Buyer> listFromBodyResponseEntityGetRequestParameter(String url, Long vehicleId) {
        ResponseEntity<List<Buyer>> exchange = restTemplate.exchange(url,
                GET, new HttpEntity<>(url, jsonUtil.createJsonHeader()),
                new ParameterizedTypeReference<>() {
                }, vehicleId);
        return exchange.getBody();
    }

    private List<Buyer> returnOrderedList(List<Buyer> list) {
        if (!list.isEmpty()) {
            return list.stream()
                    .sorted(comparing(Buyer::getId).reversed())
                    .collect(toList());
        }
        return new ArrayList<>();
    }

}
