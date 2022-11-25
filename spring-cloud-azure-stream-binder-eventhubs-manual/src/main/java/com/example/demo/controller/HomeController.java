package com.example.demo.controller;

import com.example.demo.service.EventHubsBinderManualModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    EventHubsBinderManualModeService eventHubsBinderManualModeService;

    @GetMapping("/")
    public String greeting() {
        return "Greeting from Spring Boot";
    }


    @GetMapping("/eventhubs/binder/manual")
    public String greetingFromEventHubsBinderRecordMode() {
        return "Greeting from Event Hubs Binder manual mode is: [" + eventHubsBinderManualModeService.sendAndReceiveMessage() + "]";
    }


}


