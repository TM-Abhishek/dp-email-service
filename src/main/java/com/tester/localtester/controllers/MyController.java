package com.tester.localtester.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/local/v1")
public class MyController {

    @GetMapping("/auth")
    public String getAuth(@RequestHeader("Authorization") String auth) {
        String[] authArray = auth.split(" ");
        String authString = authArray[1];
        byte[] decodedAuth = Base64.getDecoder().decode(authString);
        String[] credentials = new String(decodedAuth, StandardCharsets.UTF_8).split(":");
        String username = credentials[0];
        String password = credentials[1];
        return "Username: " + username + " Password: " + password;
    }
}