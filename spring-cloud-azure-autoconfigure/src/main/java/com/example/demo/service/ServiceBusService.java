package com.example.demo.service;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener;
import com.example.demo.config.MyServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ServiceBusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusService.class);
    private static final String DATA1 = "service bus test1";

    @Autowired
    private ServiceBusSenderClient senderClient;

    @Autowired
    private ServiceBusReceiverClient receiverClient;

    @Autowired
    private ServiceBusProcessorClient processorClient;

    @Autowired
    ServiceBusRecordMessageListener listener;

    public String sendAndReceive() {
        processorClient.stop();

        StringBuilder sb = new StringBuilder();
        senderClient.sendMessage(new ServiceBusMessage(DATA1));
        IterableStream<ServiceBusReceivedMessage> receivedMessages = receiverClient.receiveMessages(1, Duration.ofSeconds(1));
        if (receivedMessages.stream().iterator().hasNext()) {
            ServiceBusReceivedMessage message = receivedMessages.stream().iterator().next();
            LOGGER.info("The received message is {}", message.getBody());
            sb.append(message.getBody());
            receiverClient.complete(message);
        }
        return sb.toString();
    }

    public List<String> processedMessages() {
        processorClient.start();
        senderClient.sendMessage(new ServiceBusMessage(DATA1));
        if (listener instanceof MyServiceConfiguration.MyServiceBusRecordMessageListener) {
            return ((MyServiceConfiguration.MyServiceBusRecordMessageListener) listener).getMessages();
        }

        return null;
    }
}