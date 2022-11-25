package com.example.demo.aot;

import com.example.demo.service.CosmosService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class MyRuntimeHints implements RuntimeHintsRegistrar {

    private static final Log LOGGER = LogFactory.getLog(MyRuntimeHints.class);

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // Register serialization
        hints.serialization().registerType(CosmosService.User.class);
    }
}