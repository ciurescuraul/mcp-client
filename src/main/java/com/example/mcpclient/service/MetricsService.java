package com.example.mcpclient.service;

import com.example.mcpclient.model.Message;
import com.example.mcpclient.model.Metrics;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MetricsService {
    private long numRowsMissingFields;
    private long numOfMessagesWithBlankContent;
    private long numberOfRowsWithFieldsErrors;
    private Map<Long, Long> numberOfCallsByCountryCode;
    private long relationBetweenOkKoCalls;
    private long averageCallDurationGroupedByCountryCode;
    private long wordOcurrenceRankingInMessageContentField;

    public Metrics getCounters() {
        log.debug("MetricsService.getCounters()");
        List<Message> messages = ClientJsonService.messages;
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
            log.debug("numRowsMissingFields: {}", numRowsMissingFields);

            //    Number of messages with blank content
            numOfMessagesWithBlankContent = messages.stream().filter(m -> m.messageContent() == null || m.messageContent().isBlank()).count();
            log.debug("numOfMessagesWithBlankContent: {}", numOfMessagesWithBlankContent);

            //    Number of rows with fields errors
            numberOfRowsWithFieldsErrors = ClientJsonService.invalidFields.size();
            log.debug("numberOfRowsWithFieldsErrors: {}", numberOfRowsWithFieldsErrors);

            //    Number of calls origin/destination grouped by country code (https://en.wikipedia.org/wiki/MSISDN)
            numberOfCallsByCountryCode = new HashMap<>();
            long numberOfCalls = 0L;
            long countryCode;

            for (Message message : messages) {
                try {
                    String numberToParse;
                    if (!message.origin().isBlank() && message.duration() != null) {
                        numberToParse = "+" + message.origin();
                        Phonenumber.PhoneNumber msisdn = PhoneNumberUtil.getInstance().parse(numberToParse.strip(), "");
                        countryCode = msisdn.getCountryCode();

                        if (numberOfCallsByCountryCode.containsKey(countryCode)) {
                            numberOfCalls = numberOfCallsByCountryCode.get(countryCode);
                            numberOfCalls++;
                        }
                        numberOfCallsByCountryCode.put(countryCode, numberOfCalls);
                    }
                    numberOfCalls = 0L;
                } catch (NumberParseException e) {
                    log.debug("NumberParseException: {}", e.getMessage());
                }
            }
//            log.debug("number of calls: {}", numberOfCallsByCountryCode);

            //    Relationship between OK/KO calls
            relationBetweenOkKoCalls = 0L;

            //    Average call duration grouped by country code (https://en.wikipedia.org/wiki/MSISDN)
            averageCallDurationGroupedByCountryCode = 0L;

            //    Word occurrence ranking for the given words in message_content field.
            wordOcurrenceRankingInMessageContentField = 0L;
        }

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
