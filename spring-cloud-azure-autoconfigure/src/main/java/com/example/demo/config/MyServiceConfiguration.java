package com.example.demo.config;

import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsErrorHandler;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsRecordMessageListener;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyServiceConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyServiceConfiguration.class);

    @Bean
    public EventHubsRecordMessageListener eventHubsRecordMessageListener() {
       return new MyEventHubsRecordMessageListener();
    }

    @Bean
    EventHubsErrorHandler eventHubsErrorHandler() {
        return errorContext -> {
        };
    }

    @Bean
    ServiceBusRecordMessageListener serviceBusRecordMessageListener() {
        return new MyServiceBusRecordMessageListener();
    }

    @Bean
    ServiceBusErrorHandler serviceBusErrorHandler() {
        return errorContext -> {
        };
    }

    public static class MyServiceBusRecordMessageListener implements ServiceBusRecordMessageListener {

        private List<String> messages = new ArrayList<>();

        @Override
        public void onMessage(ServiceBusReceivedMessageContext message) {
            String msg = message.getMessage().getBody().toString();
            LOGGER.info("Message received from Service Bus, {}", msg);
            messages.add(msg);
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    public static class MyEventHubsRecordMessageListener implements EventHubsRecordMessageListener {

        private List<String> messages = new ArrayList<>();

        @Override
        public void onMessage(EventContext message) {
            String msg = message.getEventData().getBodyAsString();
            LOGGER.info("Message received from Event Hubs, {}", msg);
            messages.add(msg);
            message.updateCheckpoint();
        }

        public List<String> getMessages() {
            return messages;
        }
    }

}
