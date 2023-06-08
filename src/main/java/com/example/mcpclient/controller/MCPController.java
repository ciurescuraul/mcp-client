package com.example.mcpclient.controller;

import com.example.mcpclient.model.Message;
import com.example.mcpclient.service.ClientProcessJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MCPController {
    private final ClientProcessJsonService processJsonService;

    public MCPController(ClientProcessJsonService processJsonService) {
        this.processJsonService = processJsonService;
    }

    @GetMapping("/{localDate}")
    public List<Message> processJsonFile(@PathVariable("localDate") String localDate) {
        log.debug("MCPController.processJsonFile()");

        List<Message> messages = processJsonService.parseJsonFromUrlByDate(localDate);
        log.debug("messages: {}", messages);

        return messages.stream().toList();
    }


}
