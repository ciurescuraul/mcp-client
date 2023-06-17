package com.example.mcpclient.model;

import java.util.Map;

public record Metrics(
        long numberOfRowsWithMissingFields,
        long numberOfMessagesWithBlankContent,
        long numberOfRowsWithFieldsErrors,
        Map<Long, Long> numberOfCallsByCountryCode,
        long relationBetweenOkKoCalls,
        long averageCallDurationGroupedByCountryCode,
        long wordOccurrenceRankingInMessageContentField
) {
}
