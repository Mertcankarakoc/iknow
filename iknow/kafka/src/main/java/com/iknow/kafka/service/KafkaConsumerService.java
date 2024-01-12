package com.iknow.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Consumed message in second application: " + record.value());
    }
}
