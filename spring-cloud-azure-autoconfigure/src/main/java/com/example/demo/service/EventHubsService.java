// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsRecordMessageListener;
import com.example.demo.config.MyServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EventHubsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubsService.class);
    private static final String DATA = "eventhub test";

    @Autowired
    private EventHubProducerClient producerClient;

    @Autowired
    private EventHubConsumerClient consumerClient;

    @Autowired
    private EventHubsRecordMessageListener listener;

    @Autowired
    private EventProcessorClient processorClient;


    public String sendAndReceive() {
        processorClient.stop();
        producerClient.send(Arrays.asList(new EventData(DATA)));
        IterableStream<PartitionEvent> events = consumerClient.receiveFromPartition("0", 1, EventPosition.earliest());
        for (PartitionEvent event : events) {
            return event.getData().getBodyAsString();
        }

        return null;
    }

    public List<String> processedMessages() {
        processorClient.start();
        producerClient.send(Arrays.asList(new EventData(DATA)));
        if (listener instanceof MyServiceConfiguration.MyEventHubsRecordMessageListener) {
            return ((MyServiceConfiguration.MyEventHubsRecordMessageListener) listener).getMessages();
        }

        return null;
    }
}
