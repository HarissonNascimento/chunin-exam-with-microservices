package br.com.harisson.jsffrontend.persistence.request;

import br.com.harisson.core.property.JwtConfiguration;
import br.com.harisson.jsffrontend.annotation.ExceptionUnauthorized;
import br.com.harisson.jsffrontend.custom.CustomRestTemplate;
import br.com.harisson.jsffrontend.util.JsonUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import static br.com.harisson.core.util.HeaderUtil.NAME_EXPTIME_HEADER;
import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;
import static br.com.harisson.jsffrontend.util.APIUtil.URL_AUTH_LOGIN;
import static org.springframework.http.HttpMethod.POST;

public class LoginRequest implements Serializable {
    private final CustomRestTemplate restTemplate;
    private final JwtConfiguration jwtConfiguration;
    private final JsonUtil jsonUtil;

    @Inject
    public LoginRequest(CustomRestTemplate restTemplate, JwtConfiguration jwtConfiguration, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jwtConfiguration = jwtConfiguration;
        this.jsonUtil = jsonUtil;
    }

    @ExceptionUnauthorized
    public Map<String, String> loginReturnMapWithTokenAndExpirationTime(String username, String password) {
        ResponseEntity<Object> exchange = restTemplate.exchange(URL_AUTH_LOGIN.getUrl(),
                POST,
                new HttpEntity<>(jsonUtil.buildStringLoginJson(username, password), jsonUtil.createJsonHeader()),
                new ParameterizedTypeReference<>() {
                });
        return createMapWithTokenAndExpirationTime(exchange);
    }

    private Map<String, String> createMapWithTokenAndExpirationTime(ResponseEntity<Object> exchange) {
        HttpHeaders headers = exchange.getHeaders();

        Map<String, String> mapListTokenAndExpirationTime = new LinkedHashMap<>();

        mapListTokenAndExpirationTime.put(NAME_TOKEN_HEADER.getHeaderName(), headers.getFirst(jwtConfiguration.getHeader().getNameHeaderAuth()));
        mapListTokenAndExpirationTime.put(NAME_EXPTIME_HEADER.getHeaderName(), headers.getFirst(jwtConfiguration.getHeader().getNameHeaderExpTime()));

        return mapListTokenAndExpirationTime;
    }

}
