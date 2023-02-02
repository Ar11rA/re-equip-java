package self.study.example.services;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import self.study.example.dto.Transaction;
import self.study.example.dto.UserWallet;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    private final List<Transaction> transactions;
    private WalletService walletService;

    public CryptoService(WalletService walletService) {
        this.walletService = walletService;
        Security.addProvider(new BouncyCastleProvider());
        transactions = new ArrayList<>();
    }

    // ECC to sign transaction
    // elliptic curve digital signature algo (ECDSA)
    public byte[] sign(PrivateKey privateKey, String transaction) {
        try {
            Signature signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);
            signature.update(transaction.getBytes());
            return signature.sign();
        } catch (NoSuchAlgorithmException
                 | NoSuchProviderException
                 | InvalidKeyException
                 | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verify(PublicKey publicKey, String transaction, byte[] inputSignature) {
        try {
            Signature signature = Signature.getInstance("ECDSA", "BC");
            signature.initVerify(publicKey);
            signature.update(transaction.getBytes());
            return signature.verify(inputSignature);
        } catch (NoSuchAlgorithmException
                 | NoSuchProviderException
                 | InvalidKeyException
                 | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    // mempool of transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // add transaction
    public Transaction addTransaction(int userId, double amount, String receiverAddress) {
        UserWallet userWallet = walletService.getUserWallets().get(userId);
        PrivateKey privateKeyCurrentUser = userWallet.getPrivateKey();
        byte[] signature = sign(privateKeyCurrentUser, amount + receiverAddress);
        Transaction transaction = new Transaction(
          walletService.getWallets().get(userId),
          receiverAddress,
          amount,
          Base64.toBase64String(signature)
        );
        transactions.add(transaction);
        return transaction;
    }

    // verify transaction
    public boolean verifyTransaction(Transaction transaction) {
        try {
            byte[] signature = Base64.decode(transaction.getSignature());
            KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
            PublicKey currentPk = kf.generatePublic(new X509EncodedKeySpec(
              Base64.decode(transaction.getSenderAddress())
            ));
            return verify(
              currentPk,
              transaction.getAmount() + transaction.getReceiverAddress(),
              signature);
        } catch (Exception e) {
            System.out.println("Ignoring for now..." + e.getMessage());
            return false;
        }
    }
}
