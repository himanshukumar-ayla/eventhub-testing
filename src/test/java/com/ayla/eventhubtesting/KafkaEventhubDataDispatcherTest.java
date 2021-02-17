package com.ayla.eventhubtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class KafkaEventhubDataDispatcherTest {

    @InjectMocks
    KafkaEventhubDataReceiver kafkaEventhubDataReceiver;

    @Mock
    EventHubProducerService eventHubProducerService;

    @Spy
    ObjectMapper objectMapper;

    public static final String EVENTHUB_DATA_PATH = "fixtures/eventhubMessage.json";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void dispatchDataTest() throws Exception {
        doNothing().when(eventHubProducerService).send(anyString());
        kafkaEventhubDataReceiver.dispatchData(fileAsStr(KafkaEventhubDataDispatcherTest.EVENTHUB_DATA_PATH));
        verify(eventHubProducerService, times(1)).send(anyString());
    }


    public static String fileAsStr(String path) {
        String result = "";
        ClassLoader classLoader = KafkaEventhubDataDispatcherTest.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
}
