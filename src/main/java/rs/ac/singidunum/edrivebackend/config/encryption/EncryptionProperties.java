package rs.ac.singidunum.edrivebackend.config.encryption;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "encryption")
public class EncryptionProperties {
    private String rsaKeySize;
    private String aesSeySize;
    private String gcmTagSize;
    private String ivSize;
    private String algorithm;
    private String chunkSize;
}
