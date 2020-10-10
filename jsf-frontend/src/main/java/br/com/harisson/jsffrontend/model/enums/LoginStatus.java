package br.com.harisson.jsffrontend.model.enums;

import lombok.Getter;

@Getter
public enum LoginStatus {
    LOGGED("Logout"), OFF("Login");
    private final String loginStatus;

    LoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
