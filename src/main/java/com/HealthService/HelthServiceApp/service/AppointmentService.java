package com.HealthService.HelthServiceApp.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.HealthService.HelthServiceApp.model.Appointment;
import com.HealthService.HelthServiceApp.model.Patient;
import com.HealthService.HelthServiceApp.repository.ApplicationUserRepository;
import com.HealthService.HelthServiceApp.repository.AppointmentRepository;
import com.HealthService.HelthServiceApp.repository.PatientRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public Optional<Appointment> registerAppointment(Appointment appointment)
    {
        if (appointment.getPatientId() == null) {
            return Optional.empty(); // or throw new IllegalArgumentException("Patient ID is required");
        }
        return patientRepository.findById(appointment.getPatientId()).map(patient -> {
            return appointmentRepository.save(appointment);
        });
        
    }

    public Optional<Appointment> getAppointmentDetails(String appointmentId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            return appointmentRepository.findById(appointmentId);
        }
        return Optional.empty();
    }

    public Optional<List<Appointment>> getAllAppointments()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            return Optional.of(appointmentRepository.findAll());
        }
        return Optional.empty();
    }

    public Optional<List<Appointment>> getPatientAppointments(String patientId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            return Optional.of(appointmentRepository.findAll().
                                                    stream().
                                                    filter(appointment -> appointment.getPatientId().equals(patientId))
                                                            .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    public String deleteAppointment(String appointmentId)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(applicationUserRepository.findByUserName(username).isPresent())
        {
            Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
            if(appointment.isPresent())
            {
                appointmentRepository.deleteById(appointment.get().getBooking_id());
                return "Appointment deleted successfully";
            }
            return "Appointment not found";
        }
        return "Appointment not found";
    }	
}

