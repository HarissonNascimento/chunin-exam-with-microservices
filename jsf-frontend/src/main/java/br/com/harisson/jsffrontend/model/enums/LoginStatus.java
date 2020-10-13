package br.com.harisson.jsffrontend.model.enums;

import lombok.Getter;

@Getter
public enum LoginStatus {
    LOGGED("Logout"), OFF("Login");
    private final String stringLoginStatus;

    LoginStatus(String loginStatus) {
        this.stringLoginStatus = loginStatus;
    }
}
