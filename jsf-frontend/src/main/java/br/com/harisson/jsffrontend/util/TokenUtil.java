package br.com.harisson.jsffrontend.util;

import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.harisson.core.util.HeaderUtil.NAME_EXPTIME_HEADER;
import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;
import static java.util.Arrays.asList;

public class TokenUtil {
    public String getTokenFromCookiesList(HttpServletRequest request) {
        if (request.getCookies() == null) return "";
        List<Cookie> cookieList = asList(request.getCookies());
        return getCookieByKey(cookieList, NAME_TOKEN_HEADER.getHeaderName());
    }

    public boolean isExpirationTimeFromCookieValid(HttpServletRequest request) {
        if (request.getCookies() == null) return false;
        List<Cookie> cookieList = asList(request.getCookies());
        String expirationTime = getCookieByKey(cookieList, NAME_EXPTIME_HEADER.getHeaderName());
        return validateIfTimeNowIsBeforeTokenExpires(expirationTime);
    }

    private String getCookieByKey(List<Cookie> cookieList, String key) {
        return cookieList.stream().filter(cookie -> cookie.getName().equals(key))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
    }

    @SneakyThrows
    private boolean validateIfTimeNowIsBeforeTokenExpires(String expirationTime) {
        if (expirationTime.isEmpty()) return false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS").withZone(ZoneId.of("UTC"));
        LocalDateTime tokenExpirationTime = LocalDateTime.parse(expirationTime, formatter);
        return LocalDateTime.now(ZoneId.of("UTC")).isBefore(tokenExpirationTime);
    }
}
