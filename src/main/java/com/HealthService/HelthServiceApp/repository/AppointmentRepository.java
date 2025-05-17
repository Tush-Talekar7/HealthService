package com.HealthService.HelthServiceApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthService.HelthServiceApp.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,String>{

}

