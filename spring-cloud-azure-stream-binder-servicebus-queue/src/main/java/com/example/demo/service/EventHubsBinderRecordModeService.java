// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@Service
public class EventHubsBinderRecordModeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubsBinderRecordModeService.class);

    @Autowired
    private Sinks.Many<Message<String>> many;

    public String sendAndReceiveMessage() {
        String message = UUID.randomUUID().toString();
        LOGGER.info("Send a message:" + message + ".");
        many.emitNext(new GenericMessage<>(message), Sinks.EmitFailureHandler.FAIL_FAST);
        return message;
    }
}
