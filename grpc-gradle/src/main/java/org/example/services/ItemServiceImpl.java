package org.example.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.item.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class ItemServiceImpl extends ItemServiceGrpc.ItemServiceImplBase {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public ItemServiceImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = "Ecommerce_App1_Items";
    }

    @Override
    public void createItem(ItemDetails request, StreamObserver<CreateItemResponse> responseObserver) {
        // 1. Create inputs for item object
        Map<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("item_id",
          AttributeValue.builder().s(request.getId()).build());
        itemValues.put("category",
          AttributeValue.builder().s(request.getCategory()).build());
        itemValues.put("name", AttributeValue.builder().s(request.getName()).build());
        itemValues.put("description",
          AttributeValue.builder().s(request.getDescription()).build());

        // 2. Create request object for dynamo db sdk
        PutItemRequest putItemRequest = PutItemRequest.builder()
          .tableName(this.tableName)
          .item(itemValues)
          .build();
        this.dynamoDbClient.putItem(putItemRequest);

        // 3. send response to grpc client
        responseObserver.onNext(CreateItemResponse
          .newBuilder()
          .setId(request.getId())
          .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getItem(ItemRequest request,
                        StreamObserver<ItemDetails> responseObserver) {
        // 1. Create inputs for partition key and sort key
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put("item_id", AttributeValue.builder().s(request.getId()).build());
        keyToGet.put("category", AttributeValue.builder().s(request.getCategory()).build());

        // 2. Create request object for dynamo db sdk
        GetItemRequest getItemRequest = GetItemRequest.builder()
          .key(keyToGet)
          .tableName(this.tableName)
          .build();

        // 3. Get item from dynamo db
        Map<String, AttributeValue> returnedItem = this.dynamoDbClient
          .getItem(getItemRequest)
          .item();

        if (returnedItem.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
              .withDescription("Item with id " + request.getId() + " not found")
              .asRuntimeException());
            return;
        }

        // 4. Parse and send response to grpc client
        responseObserver.onNext(ItemDetails.newBuilder()
          .setId(returnedItem.get("item_id").s())
          .setCategory(returnedItem.get("category").s())
          .setName(returnedItem.get("name").s())
          .setDescription(returnedItem.get("description").s())
          .build());
        responseObserver.onCompleted();
    }

    @Override
    public void searchItemsByName(SearchItemRequest request, StreamObserver<ItemDetails> responseObserver) {
        // 1. Create search query if name contains the input from user
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":name",
          AttributeValue.builder().s(request.getName()).build());
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#input_name", "name");

        // 2. Create request object for dynamo db sdk
        ScanRequest scanRequest = ScanRequest.builder()
          .tableName(this.tableName)
          .filterExpression("contains(#input_name, :name)")
          .expressionAttributeNames(expressionAttributeNames)
          .expressionAttributeValues(expressionAttributeValues)
          .build();

        // 3. Get items from dynamo db and parse into a stream
        ScanResponse scanResponse = this.dynamoDbClient.scan(scanRequest);
        scanResponse.items()
          .forEach(item -> responseObserver.onNext(ItemDetails.newBuilder()
            .setId(item.get("item_id").s())
            .setCategory(item.get("category").s())
            .setName(item.get("name").s())
            .setDescription(item.get("description").s())
            .build()
          ));
        responseObserver.onCompleted();
    }
}
