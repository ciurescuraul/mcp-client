package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.example.mcpclient.model.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MetricsService {


//    Number of rows with fields errors
//    Number of calls origin/destination grouped by country code (https://en.wikipedia.org/wiki/MSISDN)
//    Relationship between OK/KO calls
//    Average call duration grouped by country code (https://en.wikipedia.org/wiki/MSISDN)
//    Word occurrence ranking for the given words in message_content field.

    private final ClientProcessJsonService jsonClient;

    public MetricsService(ClientProcessJsonService jsonClient) {
        this.jsonClient = jsonClient;
    }

    private long numRowsMissingFields;
    private long numOfMessagesWithBlankContent;
    private long numberOfRowsWithFieldsErrors;
    private long numberOfCallsByCountryCode;
    private long relationBetweenOkKoCalls;
    private long averageCallDurationGroupedByCountryCode;
    private long wordOcurrenceRankingInMessageContentField;

    public Metrics getCounters() {
        List<Message> messages = jsonClient.messages;
        if (!messages.isEmpty()) {
            //    Number of rows with missing fields
            numRowsMissingFields = messages.stream().filter(m -> m.messageType() == null || m.messageType().isBlank()).count() +
                    messages.stream().filter(m -> m.timestamp() == null).count() +
                    messages.stream().filter(m -> m.origin() == null).count() +
                    messages.stream().filter(m -> m.destination() == null).count() +
                    messages.stream().filter(m -> m.duration() == null).count() +
                    messages.stream().filter(m -> m.statusCode() == null || m.statusCode().isBlank()).count() +
                    messages.stream().filter(m -> m.statusDescription() == null || m.statusDescription().isBlank()).count() +
                    messages.stream().filter(m -> m.messageContent() == null || m.messageContent().isBlank()).count() +
                    messages.stream().filter(m -> m.messageStatus() == null || m.messageStatus().isBlank()).count();

            //    Number of messages with blank content
            numOfMessagesWithBlankContent = 0L;
        }

        log.info("missingMessageStatus: {}", numRowsMissingFields);

        return new Metrics(
                numRowsMissingFields,
                numOfMessagesWithBlankContent,
                numberOfRowsWithFieldsErrors,
                numberOfCallsByCountryCode,
                relationBetweenOkKoCalls,
                averageCallDurationGroupedByCountryCode,
                wordOcurrenceRankingInMessageContentField
        );
    }

}
