package com.example.mcpclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(
        @JsonProperty("message_type")
        String messageType,
        @JsonProperty("timestamp")
        Long timestamp,
        @JsonProperty("origin")
        String origin,
        @JsonProperty("destination")
        Long destination,
        @JsonProperty("duration")
        String duration,
        @JsonProperty("status_code")
        String statusCode,
        @JsonProperty("status_description")
        String statusDescription,
        @JsonProperty("message_content")
        String messageContent,
        @JsonProperty("message_status")
        String messageStatus
) {
}
