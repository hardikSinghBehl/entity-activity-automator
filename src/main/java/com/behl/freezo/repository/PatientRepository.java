package com.behl.freezo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.behl.freezo.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
