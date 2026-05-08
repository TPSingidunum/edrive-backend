package rs.ac.singidunum.edrivebackend.config.encryption;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "encryption")
public class EncryptionProperties {
    private int rsaKeySize;
    private int aesSeySize;
    private int gcmTagSize;
    private int ivSize;
    private String algorithm;
    private int chunkSize;
}
