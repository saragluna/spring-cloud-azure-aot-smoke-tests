// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import com.azure.storage.queue.QueueClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageQueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageQueueService.class);
    private static final String DATA = "storage queue test";

    @Autowired
    private QueueClient client;

    public String sendAndReceive() {
        client.create();
        client.sendMessage(DATA);
        return client.receiveMessage().getBody().toString();
    }

}
