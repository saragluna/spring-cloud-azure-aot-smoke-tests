package com.example.demo.controller;

import com.example.demo.service.EventHubsBinderRecordModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    EventHubsBinderRecordModeService eventHubsBinderRecordModeService;

    @GetMapping("/")
    public String greeting() {
        return "Greeting from Spring Boot";
    }


    @GetMapping("/eventhubs/binder/record")
    public String greetingFromEventHubsBinderRecordMode() {
        return "Greeting from Event Hubs Binder record mode is: [" + eventHubsBinderRecordModeService.sendAndReceiveMessage() + "]";
    }


}


