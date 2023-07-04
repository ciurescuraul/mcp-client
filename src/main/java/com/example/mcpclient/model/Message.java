package com.example.mcpclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(
        @Pattern(regexp = "CALL|MSG", message = "Invalid message type. Valid values are 'CALL' or 'MSG'")
        @JsonProperty("message_type")
        String messageType,

        @NotNull(message = "Timestamp is required")
        @JsonProperty("timestamp")
        Long timestamp,

        @NotNull(message = "Origin mobile identifier is required")
        @JsonProperty("origin")
        String origin,

        @NotNull(message = "Destination mobile identifier is required")
        @JsonProperty("destination")
        Long destination,

        @JsonProperty("duration")
        String duration,

        @Pattern(regexp = "OK|KO", message = "Invalid status code. Valid values are 'OK' or 'KO'")
        @JsonProperty("status_code")
        String statusCode,

        @JsonProperty("status_description")
        String statusDescription,

        @JsonProperty("message_content")
        String messageContent,

        @Pattern(regexp = "DELIVERED|SEEN", message = "Invalid message status. Valid values are 'DELIVERED' or 'SEEN'")
        @JsonProperty("message_status")
        String messageStatus
) {
}
