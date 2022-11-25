// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.demo.service;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@Service
public class CosmosService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosService.class);
    private static final String DATABASE_NAME = "TestDB";
    private static final String CONTAINER_NAME = "Users";

    @Autowired
    private CosmosClient client;

    public User saveAndGet() {
        User testUser = new User(
            "testCosmos",
            "testFirstName",
            "testLastName",
            "test address line two"
        );

        CosmosContainer container = client.getDatabase(DATABASE_NAME).getContainer(CONTAINER_NAME);

        try {
            container.deleteItem(testUser, new CosmosItemRequestOptions());
        } catch (Exception e) {
            LOGGER.warn("Exception happened while deleting", e.getMessage());
        }

        container.createItem(testUser);
        CosmosPagedIterable<User> users = container.queryItems("SELECT * FROM c WHERE c.id = 'testCosmos'",
            new CosmosQueryRequestOptions(),
            User.class);
        if (users.stream().iterator().hasNext()) {
            return users.stream().iterator().next();
        }

        return null;
    }

    public static class User implements Serializable {
        private String id;
        private String firstName;
        private String lastName;
        private String address;

        public User() {

        }

        public User(String id, String firstName, String lastName, String address) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return String.format("%s %s, %s", firstName, lastName, address);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                    user.lastName) && Objects.equals(address, user.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, firstName, lastName, address);
        }
    }

}
