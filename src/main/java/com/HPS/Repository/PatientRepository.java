package com.HPS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.HPS.Entity.PatientEntity;


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer>{
	
	// fetch Patient data by ID
	 Optional<PatientEntity> findByPatientIdAndPatientDeleteFalse(int patientId);
     
    // Find all Patient 
    @Query("SELECT p FROM PatientEntity p WHERE p.patientDelete = false")
    List<PatientEntity> findAllSoftDeleted();
}
