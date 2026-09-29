package movie_booking_system.controllers;

import jakarta.validation.Valid;
import movie_booking_system.dto.AuthResponseDTO;
import movie_booking_system.dto.SigninRequestDTO;
import movie_booking_system.dto.SignupRequestDTO;
import movie_booking_system.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDTO signupDTO) {
        String response = authService.registerUser(signupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@Valid @RequestBody SigninRequestDTO signinDTO) {
        AuthResponseDTO response = authService.loginUser(signinDTO);
        return ResponseEntity.ok(response);
    }
}

