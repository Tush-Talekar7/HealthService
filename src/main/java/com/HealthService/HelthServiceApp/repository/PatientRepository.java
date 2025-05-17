package com.HealthService.HelthServiceApp.repository;


import org.springframework.stereotype.Repository;

import com.HealthService.HelthServiceApp.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface PatientRepository extends JpaRepository<Patient,String>{

}

