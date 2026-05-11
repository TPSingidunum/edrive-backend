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
import rs.ac.singidunum.edrivebackend.dtos.auth.RegisterUserDto;
import rs.ac.singidunum.edrivebackend.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().build();
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
