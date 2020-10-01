package br.com.harisson.core.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

import static br.com.harisson.core.util.HeaderUtil.NAME_EXPTIME_HEADER;
import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;


@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Getter
@Setter
@ToString
public class JwtConfiguration implements Serializable {
    private String loginUrl = "/login/**";
    @NestedConfigurationProperty
    private Header header = new Header();
    private long expiration = 86400000L;
    private String privateKey = "lJTaUDfFV4pbqlIv4rnnw31i4qtZHEVf";
    private String type = "encrypted";

    @Getter
    @Setter
    public static class Header{
        private String nameHeaderAuth = NAME_TOKEN_HEADER.getHeaderName();
        private String nameHeaderExpTime = NAME_EXPTIME_HEADER.getHeaderName();
        private String prefix = "Bearer ";
    }
}
