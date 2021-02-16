package com.ayla.eventhubtesting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaEventhubDataReceiver extends KafkaEventhubDataDispatcher implements ConsumerSeekAware {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventhubDataReceiver.class);

    @Value("${kafka.eventhub.offset:null}")
    private String offset;

    @KafkaListener(topics = "#{'${kafka.eventhub.topic}'.split('\\s*,\\s*')}",
            groupId = "${kafka.eventhub.group.id}",
            containerFactory = "kafkaEventhubListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record) {
        logger.warn("onMessage:: ===== from eventhub topic: {}, partition: {}, offset: {}, message: {}, timestamp: {}",
                record.topic(), record.partition(), record.offset(), record.value(), record.timestamp());
        dispatchData(record.value());
    }


    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        if ("latest".equals(offset)) {
            assignments.forEach((t, o) -> callback.seekToEnd(t.topic(), t.partition()));
        } else if ("earliest".equals(offset)) {
            assignments.forEach((t, o) -> callback.seekToBeginning(t.topic(), t.partition()));
        }
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // TODO Auto-generated method stub
    }
}
