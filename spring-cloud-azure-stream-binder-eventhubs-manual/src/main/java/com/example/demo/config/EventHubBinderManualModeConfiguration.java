package com.example.demo.config;

import com.azure.spring.messaging.AzureHeaders;
import com.azure.spring.messaging.checkpoint.Checkpointer;
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
public class EventHubBinderManualModeConfiguration {


    public static final Logger LOGGER = LoggerFactory.getLogger(EventHubBinderManualModeConfiguration.class);


    @Bean
    Sinks.Many<Message<String>> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<Message<String>>> supply(Sinks.Many<Message<String>> many) {
        return () -> many.asFlux()
                .doOnNext(m -> LOGGER.info("EventHubBinderManualMode: Manually sending message {}", m.getPayload()))
                .doOnError(t -> LOGGER.error("EventHubBinderManualMode: Error encountered", t));
    }

    @Bean
    Consumer<Message<String>> consume() {
        return message -> {
            LOGGER.info("EventHubBinderManualMode: New message received: '{}'", message.getPayload());

            Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(AzureHeaders.CHECKPOINTER);
            checkpointer
                    .success()
                    .handle((r, ex) -> LOGGER.info("EventHubBinderManualMode: New message checkpointed: '{}'", message.getPayload()));

        };
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public void processError(Message sendFailedMsg) {
        LOGGER.info("EventHubBinderManualMode: receive error message: '{}'", sendFailedMsg.getPayload());
    }

}
