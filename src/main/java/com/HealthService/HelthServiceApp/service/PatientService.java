package com.HealthService.HelthServiceApp.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.HealthService.HelthServiceApp.model.Patient;
import com.HealthService.HelthServiceApp.repository.ApplicationUserRepository;
import com.HealthService.HelthServiceApp.repository.PatientRepository;
import com.HealthService.HelthServiceApp.security.JwtUtil;


@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    JwtUtil jwtUtil;

    public Optional<Patient> registerPatient(Patient patient)
    {
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            if(patientRepository.findById(patient.getPatient_Id()).isPresent())
            {
                return Optional.empty();
            }
        }

        patient.setRegisteredDate(new Date());
       
        return Optional.of(patientRepository.save(patient));
    }

    public Optional<Patient> getPatientDetails(String patientId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            return patientRepository.findById(patientId);
        }
        return Optional.empty();
    }

    public String deletePatient(String patientId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            patientRepository.deleteById(patientId);
            return "Patient deleted successfully";
        }
       return "Patient not found";
    }

    public Optional<List<Patient>> getAllPatients()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
       // System.out.println("username: " + username);
        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            return Optional.of(patientRepository.findAll());
        }
        return Optional.empty();
    }

}

