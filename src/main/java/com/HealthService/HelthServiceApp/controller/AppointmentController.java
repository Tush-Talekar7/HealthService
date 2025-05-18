package com.HealthService.HelthServiceApp.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.HealthService.HelthServiceApp.model.Appointment;
import com.HealthService.HelthServiceApp.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<Appointment> registerAppointment(@RequestBody Appointment appointment) {
       
        return appointmentService.registerAppointment(appointment)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/get/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentDetails(@PathVariable String appointmentId) {
        return appointmentService.getAppointmentDetails(appointmentId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return appointmentService.getAllAppointments()
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable String patientId) {
        return appointmentService.getPatientAppointments(patientId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable String appointmentId) {
        String result = appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok(result);
    }	
}

