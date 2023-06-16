package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientJsonService {

    @Value("${mcp.url}")
    private String linkUrl;

    @Value("${mcp.file.extension}")
    private String extension;

    @Value("${mcp.file.path}")
    private String path;
    protected static List<Message> messages;
    protected static List<String> invalidFields;
    private static final JsonMapper mapper = new JsonMapper();
    private static JsonNode jsonNode;

    public List<Message> parseJsonFromUrlByDate(String localDate) {
        log.debug("ClientJsonService.parseJsonFromUrlByDate({})", localDate);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        URL url = null;
        String line;

        try {
            url = new URL(linkUrl + localDate + extension);
        } catch (MalformedURLException e) {
            log.debug("Error: invalid URL ->  {}", e.getMessage());
        }
        if (url != null) {
            File file = new File(path + localDate + extension);
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))
            ) {
                messages = new ArrayList<>();
                invalidFields = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (isValidJSON(line)) {
                        jsonNode = mapper.readTree(line);
                        Message message = mapper.treeToValue(jsonNode, Message.class);
                        log.debug("message: {}", message);

                        if (!jsonNode.isEmpty() && !jsonNode.isNull() && jsonNode.get("message_type") != null) {
                            messages.add(message);
                            bw.write(jsonNode + "\n");
                        } else if (jsonNode.get("message_type") == null) {
                            invalidFields.add(jsonNode + "\n");
                            log.debug("Invalid json: {}", jsonNode);
                        }
                    }
                }
                log.debug("{} valid messages", messages.size());
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
            invalidFields.add(jsonNode + "\n");
            log.debug("Invalid json format: {}", json);
        }
        return valid;
    }
}
