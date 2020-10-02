package br.com.harisson.jsffrontend.filter;

import br.com.harisson.jsffrontend.util.TokenUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@WebFilter(urlPatterns = {"/*"}, description = "Session checker filter")
public class LoginFilter implements Filter, Serializable {
    @Inject
    private TokenUtil tokenUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURL().toString();
        if (url.contains("admin") && !isTokenValid(req)){
            res.sendRedirect(req.getContextPath()+"/login.xhtml");
            return;
        }
        chain.doFilter(req,res);
    }

    private boolean isTokenValid(HttpServletRequest request){
        return tokenUtil.isExpirationTimeFromCookieValid(request) && !tokenUtil.getTokenFromCookiesList(request).isEmpty();
    }
}
