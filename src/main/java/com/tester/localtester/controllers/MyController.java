package com.tester.localtester.controllers;

import com.tester.localtester.services.CSVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/")
public class MyController {

    @Autowired
    CSVExtractor csvExtractor;

    @GetMapping("/{id}")
    public void sendEmail(@PathVariable(value = "id") int id) {
        try {
            csvExtractor.sendEmailToList(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}