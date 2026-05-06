package rs.ac.singidunum.edrivebackend.config.certificates;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Klasa za Main sertifikat
@Data
@Configuration
@ConfigurationProperties(prefix = "certificate.inter")
public class InterCertProperties {
    private String path;
    private String keyPath;
    private int duration;
    private IssuerData issuer;
}
