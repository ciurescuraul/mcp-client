package com.example.mcpclient.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonValidation {

    public static final JsonMapper mapper = new JsonMapper();
    public static final List<String> invalidJson = new ArrayList<>();

    public static boolean isValidJson(final String line) {
        boolean valid = true;

        try {
            JsonNode jsonNode = mapper.readTree(line);
            if (jsonNode.isEmpty()) {
                valid = false;
                invalidJson.add(line);
            }
        } catch (JsonProcessingException e) {
            valid = false;
//            log.error("Invalid json format: {}", line);
            invalidJson.add(line);
        }

        return valid;
    }

}
