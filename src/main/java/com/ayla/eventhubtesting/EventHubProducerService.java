package com.ayla.eventhubtesting;

import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.channels.UnresolvedAddressException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.ayla.eventhubtesting.StreamUtils.createEventhubEvent;

@Component
public class EventHubProducerService {

    @Value("${eventhub.namespacename:null}")
    private String namespace;

    @Value("${eventhub.eventhubname:null}")
    private String eventhubName;

    @Value("${eventhub.saskey:null}")
    private String sasKey;

    @Value("${eventhub.saskeyname:null}")
    private String sankeyname;

    private Map<String, EventHubClient> map = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(EventHubProducerService.class);

    public void send(String message) throws EventHubException, UnresolvedAddressException {
        getEventHubClientFromMap().sendSync(createEventhubEvent(message), "key1");
        logger.warn("EventHub send:: SUCCESS message Details EventUUID");
    }

    protected EventHubClient getEventHubClientFromMap() {
        if (map.get("key1") != null) {
            return map.get("key1");
        } else {
            putEventHubClientInMap();
        }
        return map.get("key1");
    }

    protected void putEventHubClientInMap() {
        map.put("key1", getEventHubClient());
    }

    protected EventHubClient getEventHubClient() {
        try {
            ScheduledExecutorService executorService =
                    Executors.newScheduledThreadPool(1);
            EventHubClient eventHubClient = StreamUtils.buildEventHubClient(namespace, eventhubName, sasKey, sankeyname, executorService);
            logger.debug("EventHub client object created and successfully");
            return eventHubClient;
        } catch (Exception e) {
            logger.error("Failed to create Eventhub client: {}", e.getMessage());
            return null;
        }

    }

}
