package com.example.mcpclient.model;

public record Metrics(
        long numberOfRowsWithMissingFields,
        long numberOfMessagesWithBlankContent,
        long numberOfRowsWithFieldsErrors,
        long numberOfCallsByCountryCode,
        long relationBetweenOkKoCalls,
        long averageCallDurationGroupedByCountryCode,
        long wordOcurrenceRankingInMessageContentField
) {
}
