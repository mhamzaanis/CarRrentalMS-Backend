package com.Buildex.RentalCarApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheck {
    @GetMapping("/healthCheck")
    String healthCheck() {
        return "OK";
    }
}
