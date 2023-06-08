package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientProcessJsonService {

    @Value("${mcp.url}")
    private String linkUrl;

    @Value("${mcp.file.extension}")
    private String fileExtension;

    private final List<Message> messages = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<Message> parseJsonFromUrlByDate(String date) {
        log.debug("ClientProcessJsonService.parseJsonFromUrlByDate({})", date);

        URL url = null;
        String line = null;
        try {
            url = new URL(linkUrl + date + fileExtension);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));) {
            while ((line = br.readLine()) != null) {
                if (isValidJSON(line)) {
                    Message message = mapper.reader().readValue(line, Message.class);
                    messages.add(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return messages;
    }

    public static boolean isValidJSON(final String json) throws IOException {
        boolean valid = true;
        try {
            mapper.readTree(json);
        } catch (JsonProcessingException e) {
            valid = false;
        }
        return valid;
    }
}
