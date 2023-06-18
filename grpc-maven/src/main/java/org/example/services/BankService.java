package org.example.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.models.BankServiceGrpc;
import org.example.models.Account;
import org.example.models.GetAccountRequest;
import org.example.models.CreateAccountRequest;
import org.example.models.DepositRequest;
import org.example.models.WithdrawRequest;
import org.example.models.Balance;

import java.util.ArrayList;
import java.util.List;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private List<Account> accounts;
    private int currentId;

    public BankService() {
        this.accounts = new ArrayList<>();
        this.currentId = 1;
    }

    @Override
    public void getAccount(GetAccountRequest request, StreamObserver<Account> responseObserver) {
        int id = request.getId();
        Account account = accounts.stream()
          .filter(a -> a.getId() == id)
          .findFirst()
          .orElse(null);
        if (account == null) {
            Status status = Status.NOT_FOUND.withDescription("Account not found");
            responseObserver.onError(status.asRuntimeException());
        }
        else {
            responseObserver.onNext(account);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<Account> responseObserver) {
        String accountName = request.getName();
        Account account = Account.newBuilder()
          .setId(currentId)
          .setName(accountName)
          .setBalance(Balance.newBuilder().setAmount(0).build())
          .build();
        accounts.add(account);
        currentId++;
        responseObserver.onNext(account);
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(DepositRequest request, StreamObserver<Account> responseObserver) {
        long amount = request.getAmount();
        Account account = findAccount(request.getId());
        if (account == null) {
            Status status = Status.NOT_FOUND.withDescription("Account not found");
            responseObserver.onError(status.asRuntimeException());
            responseObserver.onCompleted();
            return;
        }
        Balance balance = Balance.newBuilder()
          .setAmount(account.getBalance().getAmount() + amount)
          .build();
        Account updatedAccount = account.toBuilder().
          setBalance(balance).build();
        accounts.set(account.getId() - 1, updatedAccount);
        responseObserver.onNext(updatedAccount);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Account> responseObserver) {
        long amount = request.getAmount();
        Account account = findAccount(request.getId());

        if (account == null) {
            Status status = Status.NOT_FOUND.withDescription("Account not found");
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        if (account.getBalance().getAmount() < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Insufficient funds");
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        // create new balance
        Balance balance = Balance.newBuilder()
          .setAmount(account.getBalance().getAmount() - amount)
          .build();
        // update account object
        account = account.toBuilder()
          .setBalance(balance)
          .build();
        responseObserver.onNext(account);
        responseObserver.onCompleted();
    }

    @Override
    public void withdrawStream(WithdrawRequest request, StreamObserver<Account> responseObserver) {
        long amount = request.getAmount();
        Account account = findAccount(request.getId());

        if (account == null) {
            Status status = Status.NOT_FOUND.withDescription("Account not found");
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        for (int i = 1; i <= amount; i++) {
            if (account.getBalance().getAmount() < 1) {
                Status status = Status.FAILED_PRECONDITION.withDescription("Insufficient funds");
                responseObserver.onError(status.asRuntimeException());
                return;
            }
            // create new balance object
            Balance balance = Balance.newBuilder()
              .setAmount(account.getBalance().getAmount() - 1)
              .build();
            // create new account object
            account = account.toBuilder()
              .setBalance(balance)
              .build();
            responseObserver.onNext(account);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        accounts.set(account.getId() - 1, account);
        responseObserver.onCompleted();

    }

    // find account by id and return it or null
    private Account findAccount(int id) {
        return accounts.stream()
          .filter(a -> a.getId() == id)
          .findFirst()
          .orElse(null);
    }
}
