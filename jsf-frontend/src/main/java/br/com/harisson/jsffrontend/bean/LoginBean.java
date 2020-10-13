package br.com.harisson.jsffrontend.bean;

import br.com.harisson.jsffrontend.persistence.request.LoginRequest;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

import static br.com.harisson.core.util.HeaderUtil.NAME_EXPTIME_HEADER;
import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;
import static br.com.harisson.jsffrontend.custom.CustomURLEncoderDecoder.encodeUTF8;
import static br.com.harisson.jsffrontend.model.enums.LoginStatus.LOGGED;
import static br.com.harisson.jsffrontend.model.enums.LoginStatus.OFF;

@Getter
@Setter
@Named
@SessionScoped
public class LoginBean implements Serializable {
    private boolean logged = false;
    private String statusUser = OFF.getStringLoginStatus();
    private String username;
    private String password;
    private final LoginRequest loginRequest;
    private final ExternalContext externalContext;

    @Inject
    public LoginBean(LoginRequest loginRequest, ExternalContext externalContext) {
        this.loginRequest = loginRequest;
        this.externalContext = externalContext;
    }

    public String login() {
        Map<String, String> mapWithTokenAndExpirationTime = loginRequest.loginReturnMapWithTokenAndExpirationTime(username, password);

        if (mapWithTokenAndExpirationTime == null) return null;

        addTokenAndExpirationTimeToCookies(mapWithTokenAndExpirationTime.get(NAME_TOKEN_HEADER.getHeaderName()), mapWithTokenAndExpirationTime.get(NAME_EXPTIME_HEADER.getHeaderName()));
        statusUser = LOGGED.getStringLoginStatus();
        logged = true;
        return "adminpage.xhtml?faces-redirect=true";
    }

    public String logout() {
        removeTokenAndExpirationTimeFromCookies();
        statusUser = OFF.getStringLoginStatus();
        logged = false;
        return "login.xhtml?faces-redirect=true";
    }

    private void addTokenAndExpirationTimeToCookies(String token, String expirationTime) {
        externalContext.addResponseCookie(NAME_TOKEN_HEADER.getHeaderName(), encodeUTF8(token), null);
        externalContext.addResponseCookie(NAME_EXPTIME_HEADER.getHeaderName(), expirationTime, null);
    }

    private void removeTokenAndExpirationTimeFromCookies() {
        addTokenAndExpirationTimeToCookies(null, null);
    }
}
