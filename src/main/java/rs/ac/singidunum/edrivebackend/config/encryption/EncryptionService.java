package rs.ac.singidunum.edrivebackend.config.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.*;

@Component
@Data
public class EncryptionService {
    private final EncryptionProperties encryptionProperties;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // U Production sistemu, potencijalno korisiti servis za sifrovanje Hashicort Vault, OpenBao (clone Vault-a)
    public KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
       KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
       kpg.initialize(encryptionProperties.getRsaKeySize());
       return kpg.generateKeyPair();
    }

    public void writePem(Path target, Object object) {
        try(JcaPEMWriter pw = new JcaPEMWriter(new FileWriter(target.toFile()))) {
            pw.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
