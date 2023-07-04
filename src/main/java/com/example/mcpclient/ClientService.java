package com.example.mcpclient;

import com.example.mcpclient.model.Message;
import com.example.mcpclient.util.JsonValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {
    private static final JsonMapper mapper = JsonValidation.mapper;

    public List<Message> parseJsonFromUrlByDate(String localDate) {
        Path path = getPath(localDate);
        List<Message> messages = processFile(path);
        return messages;
    }

    private Path getPath(String localDate) {
        Path path = FileSystems.getDefault().getPath("logs");
        Path file = null;

        if (Files.exists(path)) {
            log.info("Path '{}' is present", path);
            try {
                List<Path> pathList = Files.walk(path)
                        .filter(p -> p.toString().endsWith(".json"))
                        .collect(Collectors.toList());

                Optional<Path> optionalPath = pathList.stream()
                        .filter(p -> getDateFromFileName(p.toString()).contains(localDate))
                        .findFirst();

                if (optionalPath.isPresent()) {
                    file = optionalPath.get();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Path '{}' does not exist", path);
        }
        return file;
    }

    private String getDateFromFileName(String fileDate) {
        return fileDate.substring(fileDate.indexOf("_") + 1, fileDate.indexOf("."));
    }

    private List<Message> processFile(Path file) {

        try {
            List<String> lines = Files.readAllLines(file);
            List<Message> messages = processLines(lines);

            if (!messages.isEmpty()) {
                return messages;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Collections.emptyList();
    }

    private List<Message> processLines(List<String> lines) {
        List<Message> messages = new ArrayList<>();
        for (String line : lines) {
            Message message = processLine(line);
            if (message != null) messages.add(message);
        }
        return messages;
    }

    private Message processLine(String line) {
        Message message = null;
        if (JsonValidation.isValidJson(line)) {
            try {
                JsonNode jsonNode = mapper.readTree(line);
                message = convertToMessage(jsonNode);

                if (!jsonNode.isEmpty()
                        && !jsonNode.isNull()
//                        && !message.messageType().contains("NULL")
//                        && !message.messageType().isEmpty()
//                        && !message.origin().isEmpty()
                ) {
                    log.info("valid message: {}", message);
                    return message;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private Message convertToMessage(JsonNode jsonNode) throws JsonProcessingException {
        return mapper.treeToValue(jsonNode, Message.class);
    }
}
