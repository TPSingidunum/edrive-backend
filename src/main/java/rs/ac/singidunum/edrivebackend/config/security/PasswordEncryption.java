package rs.ac.singidunum.edrivebackend.config.security;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PasswordEncryption {
    private final PasswordEncoder passwordEncoder;

    {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }
}
