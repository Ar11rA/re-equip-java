package self.study.example;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import self.study.example.services.CryptoService;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ExampleApplication.class, args);
        CryptoService cryptoSvc = ctx.getBean(CryptoService.class);
        String txn = "abc";
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
            KeyPair kp = generator.generateKeyPair();
            PrivateKey privKey = kp.getPrivate();
            PublicKey pubKey = kp.getPublic();
            byte[] sign = cryptoSvc.sign(privKey, txn);
            System.out.println(cryptoSvc.verify(pubKey, "abc", sign));
            System.out.println(Base64.toBase64String(pubKey.getEncoded()));
            System.out.println(Base64.toBase64String(privKey.getEncoded()));

            KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
            PublicKey pk = kf.generatePublic(new X509EncodedKeySpec(Base64.decode(Base64.toBase64String(pubKey.getEncoded()).getBytes())));
            System.out.println(cryptoSvc.verify(pk, "abc", sign));
        } catch (Exception e) {
            System.out.println("here");
        }
    }

}
