package com.bp.accelerator.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/healthcheck")
@RestController
public class HealthcheckController {

    @GetMapping
    public long healthCheck() {
        return System.currentTimeMillis();
    }
}
