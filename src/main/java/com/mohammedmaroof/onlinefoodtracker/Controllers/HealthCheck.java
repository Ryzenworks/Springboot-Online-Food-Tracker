package com.mohammedmaroof.onlinefoodtracker.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("health-check")
public class HealthCheck {
    @GetMapping
    public String healthCheck(){
        return "Everything is Fine!";
    }
}
