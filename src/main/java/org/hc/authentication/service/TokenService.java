package org.hc.authentication.service;

import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by roxana on 27.04.2016.
 */
@Service
public class TokenService {

    @Autowired
    private CryptingService cryptingService;

    @Autowired
    private FilePropertiesReader fileReader;

    public String encode(String userName, String password, String machine) {
        String token = cryptingService.encode(fileReader.getSecret())
                + "-" + cryptingService.encode(userName)
                + "-" + cryptingService.encode(password)
                + "-" + cryptingService.encode(Long.valueOf(System.currentTimeMillis()).toString())
                + "-" + cryptingService.encode(machine);
        return token;
    }

    public boolean isExpired(String dateFromTokenString) {
        Long dateFromToken = Long.valueOf(cryptingService.decode(dateFromTokenString));
        Long dateCurrent = System.currentTimeMillis();

        return dateFromToken + fileReader.getExpiration() > dateCurrent;
    }

    public boolean isValid(String userName, String password, String machine, String token) {
        String[] splitedToken = token.split("-");

        if(checkTokenLength(splitedToken)) {
            return false;
        }

        if(!isExpired(splitedToken[3])) {
            return false;
        }

        if(!userName.equals(cryptingService.decode(splitedToken[1]))) {
            return false;
        }

        if(!machine.equals(cryptingService.decode(splitedToken[4]))) {
            return false;
        }

        return password.equals(cryptingService.decode(splitedToken[2]));
    }

    public boolean isValid(String machine, String token) {
        String[] splitedToken = token.split("-");

        if(checkTokenLength(splitedToken)) {
            return false;
        }

        if(!isExpired(splitedToken[3])) {
            return false;
        }

        if(!machine.equals(cryptingService.decode(splitedToken[4]))) {
            return false;
        }

        return true;
    }


    private boolean checkTokenLength(String[] splitedToken) {
        return splitedToken.length != 5;
    }

}
