package rs.ac.singidunum.edrivebackend.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.edrivebackend.config.exception.CustomException;
import rs.ac.singidunum.edrivebackend.config.exception.ErrorCode;
import rs.ac.singidunum.edrivebackend.config.security.PasswordEncryption;
import rs.ac.singidunum.edrivebackend.dtos.auth.RegisterUserDto;
import rs.ac.singidunum.edrivebackend.entities.User;
import rs.ac.singidunum.edrivebackend.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncryption passwordEncryption;

    public void createUser(@Valid RegisterUserDto data) {

        boolean exists = userRepository.existsByUsername(data.getUsername());
        if (exists) {
            throw new CustomException("Username is taken", HttpStatus.BAD_REQUEST, ErrorCode.USER_USERNAME_EXIST);
        }

        User user = new User();
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncryption.getPasswordEncoder().encode(data.getPassword()));

        userRepository.save(user);
    }
}
