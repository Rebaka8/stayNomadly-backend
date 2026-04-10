package com.staynomadly.api.controller;

import com.staynomadly.api.entity.User;
import com.staynomadly.api.payload.request.LoginRequest;
import com.staynomadly.api.payload.request.RegisterRequest;
import com.staynomadly.api.payload.response.ApiResponse;
import com.staynomadly.api.payload.response.JwtResponse;
import com.staynomadly.api.repository.UserRepository;
import com.staynomadly.api.security.JwtUtil;
import com.staynomadly.api.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        
        System.out.println(" ");
        System.out.println("========== LOGIN DEBUG STARTED ==========");
        System.out.println("Input Email: [" + loginRequest.getEmail() + "]");
        System.out.println("Input Password Length: " + (loginRequest.getPassword() != null ? loginRequest.getPassword().length() : "null"));

        com.staynomadly.api.entity.User dbUser = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (dbUser != null) {
             System.out.println("DB User Found! ID: " + dbUser.getId());
             System.out.println("DB Password Hash Length: " + (dbUser.getPassword() != null ? dbUser.getPassword().length() : "null"));
             boolean bCryptMatch = encoder.matches(loginRequest.getPassword(), dbUser.getPassword());
             System.out.println("Manual BCrypt.matches() Result: " + bCryptMatch);
        } else {
             System.out.println("DB Lookup: User NOT FOUND.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            System.out.println("SUCCESS: AuthenticationManager APPROVED.");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateJwtToken(authentication);
            System.out.println("SUCCESS: JWT Generated.");

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getFullName(),
                    roles
            );

            System.out.println("========== LOGIN DEBUG ENDED ==========\n");
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", jwtResponse));
        } catch (Exception e) {
            System.out.println("\n!!! EXCEPTION CAUGHT IN AUTH FLOW !!!");
            System.out.println("Exception Type: " + e.getClass().getName());
            System.out.println("Exception Message: " + e.getMessage());
            e.printStackTrace();
            System.out.println("========== LOGIN DEBUG ABORTED ==========\n");
            throw e; // Rethrow to let JwtAuthEntryPoint handle 401
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error: Email is already in use!", null));
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFullName());
        user.setRole(signUpRequest.getRole());

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully!", null));
    }
}
