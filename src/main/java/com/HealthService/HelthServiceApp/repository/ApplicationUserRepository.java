package com.HealthService.HelthServiceApp.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthService.HelthServiceApp.model.ApplicationUser;



public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{

    Optional<ApplicationUser> findByUser_name(String user_name);

}

