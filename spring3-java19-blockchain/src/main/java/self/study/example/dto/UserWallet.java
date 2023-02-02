package self.study.example.dto;

import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
public class UserWallet {

    private int id;
    private double utxoBalance;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public UserWallet(int id, PublicKey publicKey, PrivateKey privateKey) {
        this.id = id;
        this.utxoBalance = 0.0;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}
