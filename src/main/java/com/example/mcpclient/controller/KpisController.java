package com.example.mcpclient.controller;

import com.example.mcpclient.model.Kpis;
import com.example.mcpclient.service.KpisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KpisController {

    private final KpisService kpisService;

    public KpisController(KpisService kpisService) {
        this.kpisService = kpisService;
    }

    @GetMapping("/kpis")
    @ResponseStatus(HttpStatus.OK)
    public Kpis getCounters() {
        return kpisService.getCounters();
    }
}
