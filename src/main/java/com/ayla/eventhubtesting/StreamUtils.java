package com.ayla.eventhubtesting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ScheduledExecutorService;

public final class StreamUtils {

    protected static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    private static Gson gson = new GsonBuilder().create();

    private StreamUtils() {
    }


    public static EventHubClient buildEventHubClient(String nameSpace, String eventHubName,
                                                     String sasKey, String sasKeyName,
                                                     ScheduledExecutorService executorService)
            throws EventHubException, IOException {
        final ConnectionStringBuilder connectionString = new ConnectionStringBuilder()
                .setNamespaceName(nameSpace)
                .setEventHubName(eventHubName)
                .setSasKeyName(sasKeyName)
                .setSasKey(sasKey);
        return EventHubClient.createSync(connectionString.toString(), executorService);
    }


    public static EventData createEventhubEvent(String eventHubMessage) {
        byte[] payloadBytes = gson.toJson(eventHubMessage).getBytes(Charset.defaultCharset());
        return EventData.create(payloadBytes);
    }
}
