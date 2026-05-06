package rs.ac.singidunum.edrivebackend.config.certificates;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

// Klasa za Main sertifikat
@Data
@Configuration
@ConfigurationProperties(prefix = "certificate.main")
public class MainCertProperties {
    private String path;
    private String keyPath;
    private int duration;
    private IssuerData issuer;
}
