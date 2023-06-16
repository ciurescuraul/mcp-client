package com.example.mcpclient.model;

public record Kpis(
        long numberOfProcessedJsonFiles,
        long totalNumberOfRows,
        long totalNumberOfCalls,
        long totalNumberOfMessages,
        long totalNumberOfDiffOriginCountryCodes,
        long totalNumberOfDiffDestinationCountryCodes,
        long durationOfEachJsonProcess
) {
}
