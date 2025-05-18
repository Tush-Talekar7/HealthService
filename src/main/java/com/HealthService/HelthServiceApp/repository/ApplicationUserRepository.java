package com.HealthService.HelthServiceApp.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.HealthService.HelthServiceApp.model.ApplicationUser;



public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{

    @Query("SELECT u FROM ApplicationUser u WHERE u.user_name = :user_name")
    Optional<ApplicationUser> findByUserName(String user_name);

}

