package self.study.example.services;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import self.study.example.dto.UserWallet;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final List<UserWallet> userWallets;

    public WalletService() {
        this.userWallets = new ArrayList<>();
    }

    public int createWallet() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
            KeyPair kp = generator.generateKeyPair();
            PrivateKey privateKey = kp.getPrivate();
            PublicKey publicKey = kp.getPublic();
            UserWallet userWallet = new UserWallet(
              userWallets.size(), publicKey, privateKey);
            userWallets.add(userWallet);
            return userWallets.size() - 1;
        } catch (Exception e) {
            System.out.println("here");
            return -1;
        }
    }

    public Map<Integer, String> getWallets() {
        return userWallets
          .stream()
          .collect(
            Collectors.toMap(UserWallet::getId,
              userWallet -> Base64.toBase64String(userWallet.getPublicKey().getEncoded())
            )
          );
    }

    // non api usage
    public List<UserWallet> getUserWallets() {
        return userWallets;
    }
}
