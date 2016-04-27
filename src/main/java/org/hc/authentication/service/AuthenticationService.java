package org.hc.authentication.service;

import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by roxana on 27.04.2016.
 */
@Service
public class AuthenticationService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FilePropertiesReader fileReader;

    public boolean isValidHeader(String headerName, String headerValue) {
        return headerName.equalsIgnoreCase(fileReader.getHeaderName()) && headerValue.equalsIgnoreCase(fileReader.getHeaderValue());
    }

    public boolean isTokenValid(String machine, String token) {
        return tokenService.isValid(machine, token);
    }
}
