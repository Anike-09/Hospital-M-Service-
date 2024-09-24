package com.HPS.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.HPS.Entity.PatientEntity;
import com.HPS.Repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class TestRepository {
	
	 @Mock
	   private PatientRepository patientRepository;
 
	 
	 // Fetch patient by Id 
	    @Test
	    void testFindByPatientId() {
	     
	        int patientId = 1;
	        PatientEntity patient = new PatientEntity();
	        
	        patient.setPatientId(patientId);
	        patient.setPatientName("Aniket");
	        patient.setPatientAddress("kolhapur");
	        patient.setPatientDelete(false);

	        when(patientRepository.findByPatientIdAndPatientDeleteFalse(patientId))
	                .thenReturn(Optional.of(patient));
	        Optional<PatientEntity> result = patientRepository.findByPatientIdAndPatientDeleteFalse(patientId);
	        assertTrue(result.isPresent());
	        assertEquals(patientId, result.get().getPatientId());
	        assertEquals("Aniket", result.get().getPatientName());
	        assertEquals("kolhapur", result.get().getPatientAddress());
	        assertFalse(result.get().isPatientDelete());

	        verify(patientRepository, times(1)).findByPatientIdAndPatientDeleteFalse(patientId);
	    }

	    // Fetch Patient by Id , Not patient found 
	    @Test
	    void testFindByPatientIdNotExisting() {
	       
	        int patientId = 1;

	        when(patientRepository.findByPatientIdAndPatientDeleteFalse(patientId))
	                .thenReturn(Optional.empty());
        
	        Optional<PatientEntity> result = patientRepository.findByPatientIdAndPatientDeleteFalse(patientId);

	        assertFalse(result.isPresent());

	        verify(patientRepository, times(1)).findByPatientIdAndPatientDeleteFalse(patientId);
	    }

}
