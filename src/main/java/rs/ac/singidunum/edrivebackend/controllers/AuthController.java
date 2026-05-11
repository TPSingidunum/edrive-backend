package rs.ac.singidunum.edrivebackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.edrivebackend.config.exception.CustomException;
import rs.ac.singidunum.edrivebackend.config.exception.ErrorCode;
import rs.ac.singidunum.edrivebackend.config.security.JwtService;
import rs.ac.singidunum.edrivebackend.config.security.PasswordEncryption;
import rs.ac.singidunum.edrivebackend.dtos.auth.LoginUserDto;
import rs.ac.singidunum.edrivebackend.dtos.auth.RegisterUserDto;
import rs.ac.singidunum.edrivebackend.entities.User;
import rs.ac.singidunum.edrivebackend.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncryption passwordEncryption;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto data) {

        Optional<User> exists = userService.getUserByUsername(data.getUsername());

        if (exists.isEmpty()) {
            throw new CustomException("User does not exit", HttpStatus.BAD_REQUEST, ErrorCode.USER_NOT_EXIST);
        }

        if (!passwordEncryption.getPasswordEncoder().matches(data.getPassword(), exists.get().getPassword())) {
            throw new CustomException("Password is wrong", HttpStatus.BAD_REQUEST, ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        // Provera Autorizacije korisnika
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", exists.get().getEmail());
        claims.put("role", "user");
        claims.put("type", "access");

        String accessToken = jwtService.generateToken(exists.get().getUsername(), claims);

        claims.put("type", "refresh");
        String refreshToken = jwtService.generateToken(exists.get().getUsername(), claims);


        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto data) {

        if (!data.getPassword().equals(data.getConfirmPassword())) {
            throw new CustomException("Passwords do not match", HttpStatus.BAD_REQUEST, ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        userService.createUser(data);

        return ResponseEntity.ok(Map.of("success", true));
    }

}
