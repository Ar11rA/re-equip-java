package org.example.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.models.*;
import org.example.models.BankServiceGrpc;
import org.example.models.GreetServiceGrpc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoClient {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private GreetServiceGrpc.GreetServiceBlockingStub greetServiceBlockingStub;

    @BeforeAll
    public void setup() {
        System.out.println("Setting up...");
        ManagedChannel channel =  ManagedChannelBuilder.forAddress("localhost", 8080)
          .usePlaintext()
          .build();
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.greetServiceBlockingStub = GreetServiceGrpc.newBlockingStub(channel);
    }

    @Test()
    public void CreateAccount_Success() {
        CreateAccountRequest request1 = CreateAccountRequest.newBuilder()
          .setName("abc")
          .build();
        Account account1 = this.bankServiceBlockingStub.createAccount(request1);
        System.out.println(account1.getId());
        CreateAccountRequest request2 = CreateAccountRequest.newBuilder()
          .setName("abc")
          .build();
        Account account2 = this.bankServiceBlockingStub.createAccount(request2);
        System.out.println(account2.getId());
    }

    @Test
    public void GetAccount_Success() {
        GetAccountRequest request = GetAccountRequest.newBuilder()
          .setId(1)
          .build();
        Account account = this.bankServiceBlockingStub.getAccount(request);
        System.out.println(account.getName());
    }

    @Test
    public void Greet_Success() {
        GreetRequest request = GreetRequest.newBuilder()
          .setName("abc")
          .build();
        GreetResponse greeting = this.greetServiceBlockingStub.greet(request);
        System.out.println(greeting.getGreeting());
        Assertions.assertEquals("Hello abc", greeting.getGreeting());
    }

}
