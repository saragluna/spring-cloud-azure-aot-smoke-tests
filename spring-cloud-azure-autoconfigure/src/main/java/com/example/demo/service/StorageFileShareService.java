// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Service
public class StorageFileShareService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageFileShareService.class);
    private static final String DATA = "sample-file-data";

    @Value("${resource.file}")
    private Resource storageFileResource;


    public String uploadAndDownload() throws IOException {
        try (OutputStream os = ((WritableResource) storageFileResource).getOutputStream()) {
            os.write(DATA.getBytes());
        }
        return StreamUtils.copyToString(storageFileResource.getInputStream(), Charset.defaultCharset());
    }
}
