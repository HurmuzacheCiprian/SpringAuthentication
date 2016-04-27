package org.hc.authentication.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by roxana on 27.04.2016.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping(path="/endpoint", method = RequestMethod.GET)
    public ResponseEntity<String> testRequest() {
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
