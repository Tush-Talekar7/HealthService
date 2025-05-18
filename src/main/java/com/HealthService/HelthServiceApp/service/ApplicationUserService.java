package com.HealthService.HelthServiceApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import org.json.simple.JSONObject;

import com.HealthService.HelthServiceApp.model.ApplicationUser;
import com.HealthService.HelthServiceApp.repository.ApplicationUserRepository;




@Service
public class ApplicationUserService 
{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;



    public boolean registerUser(ApplicationUser user) {

        Optional<ApplicationUser> user1 = applicationUserRepository.findByUserName(user.getUser_name());

        if(user1.isPresent()) return false;
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        ApplicationUser user2 = applicationUserRepository.save(user);
        user2.setPassword("");

        return user2 != null;
        
    }
    public Optional<ApplicationUser> getUserDetails()
    {    
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationUserRepository.findByUserName(username);
    }

    public ApplicationUser updateUserDetails(ApplicationUser updatedData) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    
        return applicationUserRepository.findByUserName(currentUsername)
            .map(existingUser -> {
                existingUser.setUser_email(updatedData.getUser_email());
                existingUser.setUser_mobile(updatedData.getUser_mobile());
                existingUser.setLocation(updatedData.getLocation());
                return applicationUserRepository.save(existingUser);
            })
            .orElse(null);
    }
    
    

}


