package br.com.harisson.jsffrontend.annotation.interceptor;

import br.com.harisson.core.support.ErrorDetail;
import br.com.harisson.jsffrontend.annotation.ExceptionUnauthorized;
import br.com.harisson.jsffrontend.custom.CustomObjectMapper;
import org.springframework.web.client.HttpStatusCodeException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@ExceptionUnauthorized
public class ExceptionUnauthorizedInterceptor implements Serializable {
    private final ExternalContext externalContext;

    @Inject
    public ExceptionUnauthorizedInterceptor(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) {
        Object result = null;
        try {
            result = context.proceed();
        } catch (Exception e) {
            retrieveStringFromExceptionBodyOrPrintStackTrace(e);
        }
        return result;
    }

    private void retrieveStringFromExceptionBodyOrPrintStackTrace(Exception e) {
        try {
            HttpStatusCodeException httpException = (HttpStatusCodeException) e;
            ErrorDetail errorDetail = new CustomObjectMapper().readValue(httpException.getResponseBodyAsString(), ErrorDetail.class);
            addMessage(FacesMessage.SEVERITY_ERROR, errorDetail.getMessage(), true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void addMessage(FacesMessage.Severity severity, String msg, boolean keepMessages) {
        final FacesMessage facesMessage = new FacesMessage(severity, msg, "");
        externalContext.getFlash().setKeepMessages(keepMessages);
        externalContext.getFlash().setRedirect(true);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
