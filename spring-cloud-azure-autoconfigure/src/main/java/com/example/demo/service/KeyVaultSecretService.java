// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import com.azure.security.keyvault.secrets.SecretClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyVaultSecretService {

    @Autowired
    private SecretClient client;

    private static final String NAME = "sample-key";
    private static final String VALUE = "sample-kv-value";

    public String saveAndGet() {
        client.setSecret(NAME, VALUE);
        return client.getSecret(NAME).getValue();
    }

}
