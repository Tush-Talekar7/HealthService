package com.HealthService.HelthServiceApp.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthService.HelthServiceApp.model.Patient;
import com.HealthService.HelthServiceApp.repository.ApplicationUserRepository;
import com.HealthService.HelthServiceApp.repository.PatientRepository;
import com.HealthService.HelthServiceApp.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    JwtUtil jwtUtil;

    public Optional<Patient> registerPatient(HttpServletRequest request,Patient patient)
    {
        String token = (String)request.getAttribute("token");
        String username = jwtUtil.getUserNameFromToken(token);

        if(applicationUserRepository.findByUser_name(username).isPresent())
        {
            if(patientRepository.find(patient.getPatient_email()))
            {
                return Optional.empty();
            }
        }
       
        return Optional.of(patientRepository.save(patient));
    }

}

