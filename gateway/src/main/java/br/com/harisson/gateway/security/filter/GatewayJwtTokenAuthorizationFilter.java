package br.com.harisson.gateway.security.filter;

import br.com.harisson.core.property.JwtConfiguration;
import br.com.harisson.token.filter.JwtTokenAuthorizationFilter;
import br.com.harisson.token.token.converter.TokenConverter;
import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.com.harisson.core.util.HeaderUtil.NAME_TOKEN_HEADER;
import static br.com.harisson.token.util.SecurityContextUtil.setSecurityContext;

public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {
    public GatewayJwtTokenAuthorizationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("Duplicates")
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        String header = request.getHeader(jwtConfiguration.getHeader().getNameHeaderAuth());

        if (header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        String signedToken = tokenConverter.decryptToken(token);

        tokenConverter.validateTokenSignature(signedToken);

        setSecurityContext(SignedJWT.parse(signedToken));

        if (jwtConfiguration.getType().equalsIgnoreCase("signed")) {
            RequestContext.getCurrentContext().addZuulRequestHeader(NAME_TOKEN_HEADER.getHeaderName(), jwtConfiguration.getHeader().getPrefix() + signedToken);
        }

        filterChain.doFilter(request, response);
    }
}
