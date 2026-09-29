package movie_booking_system.services;

import movie_booking_system.dto.AuthResponseDTO;
import movie_booking_system.dto.SigninRequestDTO;
import movie_booking_system.dto.SignupRequestDTO;
import movie_booking_system.entities.User;
import movie_booking_system.enums.Role;
import movie_booking_system.exceptions.BadRequestException;
import movie_booking_system.repositories.UserRepository;
import movie_booking_system.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String registerUser(SignupRequestDTO signupDTO) {
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email address is already in use");
        }

        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

// Set Role enum directly instead of String
        if (signupDTO.getRole() != null && signupDTO.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.CUSTOMER);
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponseDTO loginUser(SigninRequestDTO signinDTO) {
        User user = userRepository.findByEmail(signinDTO.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(signinDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

// Convert Role enum to String for the JWT token payload
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponseDTO(token, user.getEmail(), user.getRole().name());
    }
}

