package com.ayla.eventhubtesting;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

public final class KafkaConfigUtil {

    private KafkaConfigUtil() {
    }

    public static AsyncListenableTaskExecutor taskExecutor(int corePoolSize, int maxPoolSize, int queueCapacity,
                                                           String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    public static Map<String, Object> getConfig(String bootstrapAddress, String groupId, boolean enableAutoCommit,
                                                int maxPollRecordsConfig, String autoOffsetReset, String sessionTimeout,
                                                String autoCommitInterval) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecordsConfig);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        // relevant only if if auto commit is true
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        return props;
    }

    public static ConsumerFactory<String, String> inOrderConsumerFactory(String bootstrapAddress, String groupId,
                                                                         boolean enableAutoCommit,
                                                                         int maxPollRecordsConfig,
                                                                         String autoOffsetReset,
                                                                         String sessionTimeout,
                                                                         String autoCommitInterval) {
        return new DefaultKafkaConsumerFactory<>(getConfig(bootstrapAddress, groupId, enableAutoCommit,
                maxPollRecordsConfig, autoOffsetReset, sessionTimeout, autoCommitInterval));
    }

    public static Map<String, Object> producerConfigs(String bootstrapAddress, int retries, int batchSize, int linger,
                                                      int bufferMemory, String acks) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        return props;
    }


    public static ProducerFactory<String, String> producerFactory(String bootstrapAddress, int retries, int batchSize,
                                                                  int linger,
                                                                  int bufferMemory, String acks) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(bootstrapAddress, retries, batchSize, linger,
                bufferMemory,
                acks));
    }
}
