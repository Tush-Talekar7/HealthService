package com.HealthService.HelthServiceApp.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.HealthService.HelthServiceApp.model.ApplicationUser;
import com.HealthService.HelthServiceApp.service.ApplicationUserService;
import com.HealthService.HelthServiceApp.security.JwtUtil;

@RestController
public class ApplicationUserController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApplicationUserService applicationUserService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody ApplicationUser loginRequest)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUser_name(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails);

            // userId here is user_name (primary key)
            String userId = userDetails.getUsername();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", jwtToken);
            response.put("userId", userId);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody ApplicationUser user) {
        try {
            boolean isRegistered = applicationUserService.registerUser(user);
            if (isRegistered) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user: " + e.getMessage());
        }
    }

    @GetMapping("/viewProfile")
    public ResponseEntity<?> getUserDetails() {
        try {
            Optional<ApplicationUser> userDetails = applicationUserService.getUserDetails();
            if (userDetails.isPresent()) {
                return ResponseEntity.ok(userDetails.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user details: " + e.getMessage());
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateUserDetails(@RequestBody ApplicationUser updatedData) {
        try {
            ApplicationUser updatedUser = applicationUserService.updateUserDetails(updatedData);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user details: " + e.getMessage());
        }
    }
}




