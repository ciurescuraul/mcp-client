package com.example.mcpclient.controller;

import com.example.mcpclient.ClientService;
import com.example.mcpclient.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MCPController {
    private final ClientService clientService;

    public MCPController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{localDate}")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> processJsonFile(@PathVariable("localDate") String localDate) {
        List<Message> messages = clientService.parseJsonFromUrlByDate(localDate);
        return messages;
    }
}
