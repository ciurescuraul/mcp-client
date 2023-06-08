package com.example.mcpclient.model;

public record Metrics(
        int numberOfRowsWithMissingFields,
        int numberOfMessagesWithBlankContent,
        int numberOfRowsWithFieldsErrors,
        int numberOfCallsByCountryCode,
        int relationBetweenOkKoCalls,
        int averageCallDurationGroupedByCountryCode,
        int wordOcurrenceRankingInMessageContentField
) {
}
