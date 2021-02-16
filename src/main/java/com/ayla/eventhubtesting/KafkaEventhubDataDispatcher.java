package com.ayla.eventhubtesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaEventhubDataDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventhubDataDispatcher.class);

    @Autowired
    EventHubProducerService eventHubProducerService;

    protected void dispatchData(String kafkaRecord) {
        try {
            eventHubProducerService.send(kafkaRecord);
        } catch (Exception e) {
            logger.error("Eventhub: sending failed for destinationUuid : {}", e.getMessage());
        }
    }
}
