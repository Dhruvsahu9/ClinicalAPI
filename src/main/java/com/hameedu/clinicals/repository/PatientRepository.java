package com.hameedu.clinicals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.hameedu.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

}
