package com.ayla.eventhubtesting;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import static com.ayla.eventhubtesting.KafkaConfigUtil.getConfig;
import static com.ayla.eventhubtesting.KafkaConfigUtil.inOrderConsumerFactory;
import static com.ayla.eventhubtesting.KafkaConfigUtil.taskExecutor;


@Configuration
@EnableKafka
public class KafkaEventhubConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventhubConsumerConfig.class);

    @Value("${kafka.eventhub.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${kafka.eventhub.enable.auto.commit:true}")
    private boolean enableAutoCommit;

    @Value("${kafka.eventhub.session.timeout}")
    private String sessionTimeout;

    @Value("${kafka.eventhub.auto.commit.interval:3000}")
    private String autoCommitInterval;

    @Value("${kafka.eventhub.group.id}")
    private String groupId;

    @Value("${kafka.eventhub.auto.offset.reset:true}")
    private String autoOffsetReset;

    @Value("${kafka.eventhub.concurrency:4}")
    private int concurrency;

    @Value("${kafka.eventhub.maxPollRecordsConfig:10}")
    private int maxPollRecordsConfig;

    @Value("${kafka.eventhub.pollTimeout:5000}")
    private int pollTimeout;

    @Value("${kafka.eventhub.corepoolsize:64}")
    private int corePoolSize;

    @Value("${kafka.eventhub.maxpoolsize:128}")
    private int maxPoolSize;

    @Value("${kafka.eventhub.queuecapacity:128}")
    private int queueCapacity;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaEventhubListenerContainerFactory() {
        logger.info("kafkaEventhubListenerContainerFactory:: bootstrapAddress: {}", bootstrapAddress);
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(inOrderConsumerFactory(bootstrapAddress, groupId, enableAutoCommit,
                maxPollRecordsConfig,
                autoOffsetReset, sessionTimeout, autoCommitInterval));
        factory.setConcurrency(concurrency);
        factory.getContainerProperties()
                .setPollTimeout(pollTimeout);
        factory.getContainerProperties()
                .setConsumerTaskExecutor(taskExecutor(corePoolSize, maxPoolSize, queueCapacity,
                        "EVENTHUB-CSMR-"));
        return factory;
    }

    @Bean(name = "eventhub")
    public AdminClient adminClient() {
        return AdminClient.create(getConfig(bootstrapAddress, groupId, enableAutoCommit, maxPollRecordsConfig,
                autoOffsetReset, sessionTimeout, autoCommitInterval));
    }
}
