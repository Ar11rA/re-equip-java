package self.study.example.dto;

import lombok.Data;

@Data
public class InputTransaction {
    private String receiverAddress;
    private double amount;
}
