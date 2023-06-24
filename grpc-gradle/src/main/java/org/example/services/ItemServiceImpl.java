package org.example.services;

import io.grpc.stub.StreamObserver;
import org.example.item.CreateItemResponse;
import org.example.item.ItemDetails;
import org.example.item.ItemRequest;
import org.example.item.ItemServiceGrpc;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

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
        Map<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("item_id",
          AttributeValue.builder().s(request.getId()).build());
        itemValues.put("category",
          AttributeValue.builder().s(request.getCategory()).build());
        itemValues.put("name", AttributeValue.builder().s(request.getName()).build());
        itemValues.put("description",
          AttributeValue.builder().s(request.getDescription()).build());
        PutItemRequest putItemRequest = PutItemRequest.builder()
          .tableName(this.tableName)
          .item(itemValues)
          .build();
        this.dynamoDbClient.putItem(putItemRequest);
        responseObserver.onNext(CreateItemResponse
          .newBuilder()
          .setId(request.getId())
          .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getItem(ItemRequest request,
                        StreamObserver<ItemDetails> responseObserver) {
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put("item_id", AttributeValue.builder().s(request.getId()).build());
        keyToGet.put("category", AttributeValue.builder().s(request.getCategory()).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
          .key(keyToGet)
          .tableName(this.tableName)
          .build();

        Map<String, AttributeValue> returnedItem = dynamoDbClient
          .getItem(getItemRequest)
          .item();

        responseObserver.onNext(ItemDetails.newBuilder()
          .setId(returnedItem.get("item_id").s())
          .setCategory(returnedItem.get("category").s())
          .setName(returnedItem.get("name").s())
          .setDescription(returnedItem.get("description").s())
          .build());
        responseObserver.onCompleted();
    }
}
