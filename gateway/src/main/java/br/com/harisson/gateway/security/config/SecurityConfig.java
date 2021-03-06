package br.com.harisson.gateway.security.config;

import br.com.harisson.core.property.JwtConfiguration;
import br.com.harisson.gateway.security.filter.GatewayJwtTokenAuthorizationFilter;
import br.com.harisson.token.config.SecurityTokenConfig;
import br.com.harisson.token.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {
    private final TokenConverter tokenConverter;


    public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(GET, "/spring-backend/**").permitAll()
                .antMatchers(POST, "/spring-backend/**/buyer/admin").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .and()
                .addFilterAfter(new GatewayJwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);

        super.configure(http);
    }
}
