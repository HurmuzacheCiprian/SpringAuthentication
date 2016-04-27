package org.hc.authentication.resources;

import org.hc.authentication.dto.AuthenticationResponse;
import org.hc.authentication.dto.TokenResponse;
import org.hc.authentication.dto.UserDto;
import org.hc.authentication.dto.UserTokenDto;
import org.hc.authentication.service.TokenService;
import org.hc.authentication.util.FilePropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by roxana on 24.04.2016.
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private FilePropertiesReader reader;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest servletRequest;

    @RequestMapping(path="/token", method = RequestMethod.POST)
    public ResponseEntity<TokenResponse> testTokenResponse(@RequestBody UserDto userDto) {
        TokenResponse tokenResponse = TokenResponse.builder().token(tokenService.encode(userDto.getUser(), userDto.getPassword(),servletRequest.getServerName())).build();
        return new ResponseEntity<TokenResponse>(tokenResponse, HttpStatus.OK);
    }

    @RequestMapping(path="/token/check", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> testTokenResponse(@RequestBody UserTokenDto tokenDto) {
        return tokenService.isValid(tokenDto.getUser(),tokenDto.getPassword(),servletRequest.getServerName(),tokenDto.getToken()) ?
                new ResponseEntity<AuthenticationResponse>(AuthenticationResponse.builder().message("valid").build(), HttpStatus.OK) :
                new ResponseEntity<AuthenticationResponse>(AuthenticationResponse.builder().message("invalid").build(),HttpStatus.BAD_REQUEST);
    }


}
