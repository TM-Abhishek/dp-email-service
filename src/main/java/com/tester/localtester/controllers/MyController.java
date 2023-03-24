package com.tester.localtester.controllers;

import com.tester.localtester.services.CSVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/")
public class MyController {

    @Autowired
    CSVExtractor csvExtractor;

    @GetMapping("/")
    public void sendEmail() {
        try {
            csvExtractor.sendEmailToList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}