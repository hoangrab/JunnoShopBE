package com.juno.controller;

import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.request.GoogleRequest;
import com.juno.request.LoginRequest;
import com.juno.request.RegisterRequest;
import com.juno.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("login/oauth2")
    public ResponseEntity<?> oath2(@RequestBody LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + loginRequest.getToken();
        try {
            ResponseEntity<GoogleRequest> response = restTemplate.getForEntity(url, GoogleRequest.class);
            return ResponseEntity.status(HttpStatus.OK).body(userService.loginAuth2(Objects.requireNonNull(response.getBody())));
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IdToken invalid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }
    }


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String,Object> map = userService.login(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        try {
            userService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (ResourceAlreadyExitsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("UserName already exists");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }
    }
}
