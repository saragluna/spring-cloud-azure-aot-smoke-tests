package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class ServiceBusQueueBinderConfiguration {


    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusQueueBinderConfiguration.class);


    @Bean
    Sinks.Many<Message<String>> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<Message<String>>> supply(Sinks.Many<Message<String>> many) {
        return () -> many.asFlux()
                .doOnNext(m -> LOGGER.info("ServiceBusQueueBinder: Manually sending message {}", m.getPayload()))
                .doOnError(t -> LOGGER.error("ServiceBusQueueBinder: Error encountered", t));
    }

    @Bean
    Consumer<Message<String>> consume() {
        return message -> {
            LOGGER.info("ServiceBusQueueBinder: New message received: '{}'", message.getPayload());
        };
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public void processError(Message sendFailedMsg) {
        LOGGER.info("ServiceBusQueueBinder: receive error message: '{}'", sendFailedMsg.getPayload());
    }

}
