package org.hc.authentication.resources;

import org.hc.authentication.configuration.ConfigurationHeaders;
import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by roxana on 24.04.2016.
 */
@RestController
@RequestMapping("/test")
public class TestRest {

    @Autowired
    private FilePropertiesReader reader;

    @RequestMapping(path="/header")
    public ResponseEntity<String> testResponse(@RequestHeader(value = "H-Test-App") String applicationIdHeader) {

        if(reader.getHeaderValue().equals(applicationIdHeader)) {
            return new ResponseEntity<String>("Custom header checked", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Bad request!", HttpStatus.BAD_REQUEST);

    }


}
