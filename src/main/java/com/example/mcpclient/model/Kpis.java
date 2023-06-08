package com.example.mcpclient.model;

public record Kpis(
        int numberOfProcessedJsonFiles,
        int totalNumberOfRows,
        int totalNumberOfCalls,
        int totalNumberOfMessages,
        int totalNumberOfDiffOriginCountryCodes,
        int totalNumberOfDiffDestinationCountryCodes,
        int durationOfEachJsonProcess
) {
}
