package com.HealthService.HelthServiceApp.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthService.HelthServiceApp.model.Appointment;
import com.HealthService.HelthServiceApp.model.Patient;
import com.HealthService.HelthServiceApp.repository.AppointmentRepository;
import com.HealthService.HelthServiceApp.repository.PatientRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Optional<Appointment> registerAppointment(Appointment appointment)
    {
        if (appointment.getPatientId() == null) {
            return Optional.empty(); // or throw new IllegalArgumentException("Patient ID is required");
        }
        return patientRepository.findById(appointment.getPatientId()).map(patient -> {
            return appointmentRepository.save(appointment);
        });
        
    }
	
}

