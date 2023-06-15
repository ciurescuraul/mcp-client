package com.example.mcpclient.controller;

import com.example.mcpclient.model.Metrics;
import com.example.mcpclient.service.MetricsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MetricsController {
    // The service will have an HTTP endpoint (/metrics) that returns a set of counters related with the processed JSON file

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    @ResponseStatus(HttpStatus.OK)
    public Metrics getCounters() {
        return metricsService.getCounters();
    }
}
