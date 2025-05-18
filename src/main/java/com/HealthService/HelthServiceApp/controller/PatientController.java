package com.HealthService.HelthServiceApp.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.HealthService.HelthServiceApp.model.Patient;
import com.HealthService.HelthServiceApp.service.PatientService;
@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<String> registerPatient(@RequestBody Patient patient)
    {
        Optional<Patient> patientOptional = patientService.registerPatient(patient);
        if(patientOptional.isPresent())
        {
            return ResponseEntity.ok().body("Patient registered successfully");
        }
        return ResponseEntity.badRequest().body("Patient registration failed");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients()
    {
       // System.out.println("getAllPatients");
        return ResponseEntity.ok().body(patientService.getAllPatients().get());
    }

    
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable String patientId)
    {
        Optional<Patient> patientOptional = patientService.getPatientDetails(patientId);

        if (patientOptional.isPresent()) {
            return ResponseEntity.ok().body(patientOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String patientId)
    {
        Optional<Patient> patientOptional = patientService.getPatientDetails(patientId);
        if (patientOptional.isPresent()) {
            patientService.deletePatient(patientId);
            return ResponseEntity.ok().body("Patient deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
    }
	
}

