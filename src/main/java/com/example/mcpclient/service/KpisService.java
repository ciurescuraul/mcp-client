package com.example.mcpclient.service;

import com.example.mcpclient.model.Kpis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KpisService {
    private long numberOfProcessedJsonFiles;
    private long totalNumberOfRows;
    private long totalNumberOfCalls;
    private long totalNumberOfMessages;
    private long totalNumberOfDiffOriginCountryCodes;
    private long totalNumberOfDiffDestinationCountryCodes;
    private long durationOfEachJsonProcess;

    public Kpis getCounters() {

        return new Kpis(
                numberOfProcessedJsonFiles,
                totalNumberOfRows,
                totalNumberOfCalls,
                totalNumberOfMessages,
                totalNumberOfDiffOriginCountryCodes,
                totalNumberOfDiffDestinationCountryCodes,
                durationOfEachJsonProcess
        );
    }
}
