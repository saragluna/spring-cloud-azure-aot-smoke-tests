package com.example.demo.controller;

import com.example.demo.service.AppConfigurationService;
import com.example.demo.service.CosmosService;
import com.example.demo.service.EventHubsService;
import com.example.demo.service.KeyVaultSecretService;
import com.example.demo.service.ServiceBusService;
import com.example.demo.service.StorageBlobService;
import com.example.demo.service.StorageFileShareService;
import com.example.demo.service.StorageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HomeController {

    @Value("${my.property:}")
    private String val;

    @Autowired
    AppConfigurationService appConfigurationService;

    @Autowired
    CosmosService cosmosService;

    @Autowired
    EventHubsService eventHubsService;

    @Autowired
    KeyVaultSecretService secretService;

    @Autowired
    ServiceBusService serviceBusService;

    @Autowired
    StorageBlobService storageBlobService;

    @Autowired
    StorageFileShareService storageFileShareService;

    @Autowired
    StorageQueueService storageQueueService;

    @GetMapping("/")
    public String greeting() {
        return "Greeting from Spring Boot";
    }

    @GetMapping("/appconfiguration")
    public String greetingFromAppConfiguration() {
        return "Greeting from App Configuration is: [" + appConfigurationService.saveAndGet() + "]";
    }

    @GetMapping("/cosmos")
    public String greetingFromCosmos() {
        return "Greeting from Cosmos is: [" + cosmosService.saveAndGet() + "]";
    }

    @GetMapping("/eventhubs")
    public String greetingFromEventHubs() {
        return "Greeting from Event Hubs is: [" + eventHubsService.sendAndReceive() + "]";
    }

    @GetMapping("/eventhubs/processed")
    public String greetingFromEventHubsProcessor() {
        return "Greeting from Event Hubs Processor is: [" + eventHubsService.processedMessages() + "]";
    }

    @GetMapping("/keyvault/propertySource")
    public String greetingFromKeyVaultPropertySource() {
        return "Greeting from Key Vault is: [" + this.val + "]";
    }

    @GetMapping("/keyvault/secret")
    public String greetingFromKeyVaultSecret() {
        return "Greeting from Key Vault is: [" + secretService.saveAndGet() + "]";
    }

    @GetMapping("/servicebus")
    public String greetingFromServiceBus() {
        return "Greeting from Service Bus is: [" + serviceBusService.sendAndReceive() + "]";
    }

    @GetMapping("/servicebus/processed")
    public String greetingFromServiceBusProcessor() {
        return "Greeting from Service Bus Processor is: [" + serviceBusService.processedMessages() + "]";
    }

    @GetMapping("/storage/blob")
    public String greetingFromStorageBlob() throws IOException {
        return "Greeting from Storage Blob is: [" + storageBlobService.uploadAndDownload() + "]";
    }

    @GetMapping("/storage/fileshare")
    public String greetingFromStorageFile() throws IOException {
        return "Greeting from Storage File Share is: [" + storageFileShareService.uploadAndDownload() + "]";
    }

    @GetMapping("/storage/queue")
    public String greetingFromStorageQueue() {
        return "Greeting from Storage Queue is: [" + storageQueueService.sendAndReceive() + "]";
    }

}


