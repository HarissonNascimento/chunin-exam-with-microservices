package br.com.harisson.core.util;

import lombok.Getter;

@Getter
public enum HeaderUtil {
    NAME_TOKEN_HEADER("Authorization"),
    NAME_EXPTIME_HEADER("ExpirationTime");

    private final String headerName;

    HeaderUtil(String headerName){
        this.headerName = headerName;
    }
}
