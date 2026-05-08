package rs.ac.singidunum.edrivebackend.config.certificates;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import rs.ac.singidunum.edrivebackend.config.encryption.EncryptionService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;

@Component
@RequiredArgsConstructor
public class CertificateInitializer implements ApplicationRunner {

    // Services
    private final CertificateService certificateService;
    private final EncryptionService encryptionService;

    // Properties
    private final MainCertProperties mainCertProperties;
    private final InterCertProperties interCertProperties;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        KeyPair mainKP = encryptionService.generateRSAKeyPair();
        X509Certificate mainCert = certificateService.generateMainCertificate(mainKP, mainCertProperties);

        KeyPair interKP = encryptionService.generateRSAKeyPair();
        X509Certificate interCert = certificateService.generateInterCertificate(interKP, mainKP, mainCert, interCertProperties, mainCertProperties);

        Path mainCertPath = Path.of(mainCertProperties.getPath());
        Path interCertPath = Path.of(interCertProperties.getPath());

        if (Files.notExists(mainCertPath)) {
            Files.createDirectories(mainCertPath.getParent());
        }

        if (Files.notExists(interCertPath)) {
            Files.createDirectories(interCertPath.getParent());
        }

        // Main Certificates
        encryptionService.writePem(mainCertPath, mainCert);
        encryptionService.writePem(Path.of(mainCertProperties.getKeyPath()), mainKP.getPrivate());

        // Inter Certificates
        encryptionService.writePem(interCertPath, interCert);
        encryptionService.writePem(Path.of(interCertProperties.getKeyPath()), interKP.getPrivate());
    }
}
