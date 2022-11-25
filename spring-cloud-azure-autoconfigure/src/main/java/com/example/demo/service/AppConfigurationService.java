// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import com.azure.core.exception.ResourceExistsException;
import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.models.ConfigurationSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConfigurationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigurationService.class);
    private static final String SAMPLE_KEY = "sample-key";
    private static final String SAMPLE_LABEL = "sample-label";
    private static final String SAMPLE_VALUE = "sample-appconfig-value";

    @Autowired
    private ConfigurationClient client;

    public String saveAndGet() {
        try {
            client.addConfigurationSetting(SAMPLE_KEY, SAMPLE_LABEL, SAMPLE_VALUE);
        } catch (ResourceExistsException e) {
            LOGGER.warn("Resource exists, {}", e.getLocalizedMessage());
        }
        ConfigurationSetting configurationSetting = client.getConfigurationSetting(SAMPLE_KEY, SAMPLE_LABEL);
        return configurationSetting.getValue();
    }

}
