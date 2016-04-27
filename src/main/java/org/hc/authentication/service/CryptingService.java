package org.hc.authentication.service;

import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by roxana on 24.04.2016.
 */
@Component
public class CryptingService {

    @Autowired
    private FilePropertiesReader fileReader;

    private static final String specialChars = "B|2T!=+s$*0GBRE_@(#&DKWSAN@#@)!$FAMAW@!*$W_DSA#$#U!*)_@!5+#DSAFN";
    private static final String specialChars1 = "NV934l_=!dfsMFuisfbS$fasfnoSDYIds34CAS31AsGD80FD21j";

    private final Integer max =  25232;
    private final Integer min = 0;

    public String encode(String string) {
        String secret = fileReader.getSecret();
        String token = "" ;
        final Random random = new Random();

        for(int i=0;i<string.length();i++) {
            char secretChar = secret.charAt((random.nextInt((max - min) + 1)+min) % secret.length());
            char secretChar1 = specialChars.charAt((random.nextInt((max - min) + 1)+min) % specialChars.length());
            char currentChar = string.charAt(i);
            char secretChar2 = specialChars1.charAt((random.nextInt((max - min) + 1)+min) % specialChars1.length());
            char secretChar3 = string.charAt((random.nextInt((max - min) + 1)+min) % string.length());

            token+=secretChar;
            token+=secretChar1;
            token+=currentChar;
            token+=secretChar2;
            token+=secretChar3;
        }
        return token;
    }

    public String decode(String encodedString) {
        String token = "" ;
        int start = 2;
        while(start < encodedString.length()) {
            token += encodedString.charAt(start);
            start+=5;
        }
        return token;
    }


    public static void main(String[] args) {
        CryptingService tokenService = new CryptingService();
        String encoded = tokenService.encode("admin");
        System.out.println("encoded:"+encoded);
        System.out.println("decoded:"+tokenService.decode(encoded));



    }

}
