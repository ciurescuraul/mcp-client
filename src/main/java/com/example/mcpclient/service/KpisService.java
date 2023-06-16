package com.example.mcpclient.service;

import com.example.mcpclient.model.Kpis;
import com.example.mcpclient.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KpisService {
    //    Total number of processed JSON files
    //    Total number of rows
    //    Total number of calls
    //    Total number of messages
    //    Total number of different origin country codes (https://en.wikipedia.org/wiki/MSISDN)
    //    Total number of different destination country codes (https://en.wikipedia.org/wiki/MSISDN)
    //    Duration of each JSON process
    private long numberOfProcessedJsonFiles;
    private long totalNumberOfRows;
    private long totalNumberOfCalls;
    private long totalNumberOfMessages;
    private long totalNumberOfDiffOriginCountryCodes;
    private long totalNumberOfDiffDestinationCountryCodes;
    private long durationOfEachJsonProcess;

    public Kpis getCounters() {
        log.debug("KpisService.getCounters()");
        List<Message> messages = ClientJsonService.messages;


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
