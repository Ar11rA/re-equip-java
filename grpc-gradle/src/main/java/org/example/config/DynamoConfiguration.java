package org.example.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class DynamoConfiguration {
    public static DynamoDbClient getClient() {
        return DynamoDbClient.builder()
          .endpointOverride(URI.create(System.getenv("DYNAMODB_ENDPOINT")))
          .region(Region.of(System.getenv("AWS_REGION")))
          .build();
    }
}
