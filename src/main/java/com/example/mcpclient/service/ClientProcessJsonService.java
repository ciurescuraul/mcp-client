package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ClientProcessJsonService {

    @Value("${mcp.url}")
    private String linkUrl;

    @Value("${mcp.file}")
    private String fileExtension;
    protected static List<Message> messages = Collections.emptyList();
    private static final JsonMapper mapper = new JsonMapper();

    public List<Message> parseJsonFromUrlByDate(String localDate) {
        log.debug("ClientProcessJsonService.parseJsonFromUrlByDate({})", localDate);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
//        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

        URL url = null;
        String line;
        try {
            url = new URL(linkUrl + localDate + fileExtension);
        } catch (MalformedURLException e) {
            log.debug("Error: invalid URL ->  {}", e.getMessage());
        }
        if (url != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                messages = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (isValidJSON(line)) {
                        Message message = mapper.reader().readValue(line, Message.class);
                        messages.add(message);
                    }
                }
            } catch (IOException e) {
                log.debug("Error: cannot process, invalid URL ->  {}", e.getMessage());
            }
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
