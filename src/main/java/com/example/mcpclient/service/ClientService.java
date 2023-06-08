package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String URL_LINK = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_";
    private static final String EXTENSION = ".json";

    private static final List<Message> messages = new ArrayList<>();

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Message> parseJsonFromUrlByDate(String date) {
        BufferedReader br = null;
        try {
            URL url = new URL(URL_LINK + date + EXTENSION);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                if(isValidJSON(line)){
                    Message message = mapper.reader().readValue(line, Message.class);
                    messages.add(message);
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return messages;
    }

    public static boolean isValidJSON(final String json) throws IOException {
        boolean valid = true;
        try{
            mapper.readTree(json);
        } catch(JsonProcessingException e){
            valid = false;
        }
        return valid;
    }
}
