package br.com.harisson.jsffrontend.util;

import br.com.harisson.core.model.Vehicle;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.faces.annotation.RequestCookieMap;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import java.util.Map;

import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;
import static br.com.harisson.jsffrontend.custom.CustomURLEncoderDecoder.dencodeUTF8;

public class JsonUtil {
    @Inject
    @RequestCookieMap
    private Map<String, Object> cookieMap;

    public HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public HttpHeaders createTokenizedHeader() {
        HttpHeaders headers = createJsonHeader();
        Cookie tokenCookie = (Cookie) cookieMap.get(NAME_TOKEN_HEADER.getHeaderName());
        headers.add(NAME_TOKEN_HEADER.getHeaderName(),
                dencodeUTF8(tokenCookie.getValue()));
        return headers;
    }

    public <E> HttpEntity<E> tokenizedHttpEntityHeader(E e) {
        return new HttpEntity<>(e, createTokenizedHeader());
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public String addQuotes(String value) {
        return new StringBuilder().append("\"").append(value).append("\"").toString();
    }

    public String buildStringLoginJson(String username, String password) {
        return "{\"username\":" + addQuotes(username) + ",\"password\":" + addQuotes(password) + "}";
    }

    public String buildStringJsonVehicle(Vehicle vehicle) {
        return "{\"id\":" + vehicle.getId()
                + ",\"price\":" + vehicle.getPrice()
                + ",\"year\":" + vehicle.getYear()
                + ",\"vehicleType\":" + addQuotes(vehicle.getVehicleType())
                + ",\"fuelType\":" + addQuotes(vehicle.getFuelType())
                + ",\"transmissionType\":" + addQuotes(vehicle.getTransmissionType())
                + ",\"vehicleManufacturer\":" + addQuotes(vehicle.getVehicleManufacturer())
                + ",\"model\":" + addQuotes(vehicle.getModel())
                + ",\"description\":" + addQuotes(vehicle.getDescription())
                + ",\"imagesFolderDirectory\":" + addQuotes(vehicle.getImagesFolderDirectory())
                + ",\"sold\":" + vehicle.isSold();
    }
}
