package self.study.example.dto;

import lombok.Data;

@Data
public class Transaction {
    private String senderAddress;
    private String receiverAddress;
    private double amount;
    private String signature;

    public Transaction(String senderAddress, String receiverAddress, double amount, String signature) {
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
        this.amount = amount;
        this.signature = signature;
    }
}
