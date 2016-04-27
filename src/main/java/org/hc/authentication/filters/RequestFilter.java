package org.hc.authentication.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hc.authentication.service.AuthenticationService;
import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by roxana on 24.04.2016.
 */
@Component
public class RequestFilter implements Filter{

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FilePropertiesReader fileReader;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String headerValue = request.getHeader(fileReader.getHeaderName());
        Enumeration<String> headers = request.getHeaderNames();
        String header = "";
        String tokenHeader = "";
        while(headers.hasMoreElements() && StringUtils.isEmpty(header)) {
            String currentHeader = headers.nextElement();
            if(currentHeader.equalsIgnoreCase(fileReader.getHeaderName())) {
                header = currentHeader;
            }
            if(currentHeader.equalsIgnoreCase(fileReader.getTokenHeader())) {
                tokenHeader = currentHeader;
            }
        }

        if(!request.getRequestURI().equalsIgnoreCase("/authentication/token")) {
            if(!authenticationService.isValidHeader(header,headerValue) || !isValidToken(request, tokenHeader)) {
                response.setStatus(401);
                return ;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValidToken(HttpServletRequest request, String tokenHeader) {
        String tokenHeaderValue = request.getHeader(fileReader.getTokenHeader());
        if(StringUtils.isEmpty(tokenHeaderValue) && StringUtils.isEmpty(tokenHeader)) {
            return false;
        } else {
            if(authenticationService.isTokenValid(request.getServerName(), tokenHeaderValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
