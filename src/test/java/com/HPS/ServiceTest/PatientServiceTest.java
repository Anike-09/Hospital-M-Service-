package com.HPS.ServiceTest;




import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.HPS.Entity.PatientEntity;
import com.HPS.Exception.IdNotFoundException;
import com.HPS.Exception.SomeThingWentWrongException;
import com.HPS.Repository.PatientRepository;
import com.HPS.Service.PatientService;

public class PatientServiceTest {
	
	@Mock
    private PatientRepository patientRepository;
	
	@Mock
	private Logger logger;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    // Test save test case
    @Test
    void testSavePatient() {
        
    	PatientEntity Patient = new PatientEntity();

        Patient.setPatientAddress("kolhapur");
        Patient.setPatientAge(25);
        Patient.setPatientName("Aniket");
        Patient.setPatientId(1);

        PatientEntity savedPatient = new PatientEntity();
        savedPatient.setPatientId(1);
        savedPatient.setPatientName("Aniket");
        savedPatient.setPatientAge(25);

        when(patientRepository.save(Patient)).thenReturn(savedPatient);

     
        PatientEntity result = patientService.savePatient(Patient);

     
//        assertNotNull(result);
//        assertEquals("Aniket", result.setPatientAddress("kolhapur"));
//        assertEquals(1L, result.setPatientId(1));
//        assertEquals("Aniket", result.setPatientName());
//        assertEquals(30, result.setPatientAge());
//
        verify(patientRepository, times(1)).save(Patient);
    }
    
    
    @Test
    void testFetchPatient_WhenPatientExists() {
       
        int patientId = 1;
        PatientEntity patient = new PatientEntity();
        patient.setPatientId(patientId);
        patient.setPatientName("John Doe");
        patient.setPatientDelete(false);

        when(patientRepository.findByPatientIdAndPatientDeleteFalse(patientId))
            .thenReturn(Optional.of(patient));

     
        Optional<PatientEntity> result = patientService.fetchPentient(patientId);

      
        assertTrue(result.isPresent());
        assertEquals(patientId, result.get().getPatientId());
   //     assertEquals("Aniket", result.get().setPatientName("Aniket"));
        assertFalse(result.get().isPatientDelete());

        verify(patientRepository, times(1)).findByPatientIdAndPatientDeleteFalse(patientId);
    }

     // Fetch Patient test case
    @Test
    void testFetchPatient() {
        
        int patientId = 1;

        when(patientRepository.findByPatientIdAndPatientDeleteFalse(patientId))
            .thenReturn(Optional.empty());

      
        Optional<PatientEntity> result = patientService.fetchPentient(patientId);

     
        assertFalse(result.isPresent());

        verify(patientRepository, times(1)).findByPatientIdAndPatientDeleteFalse(patientId);
    }
     
    
    // Fetch all patient 
    @Test
    void testFetchAllPatients() {
  
        PatientEntity patient1 = new PatientEntity();
        patient1.setPatientId(1);
        patient1.setPatientName("Aniket");
        patient1.setPatientDelete(false);

        PatientEntity patient2 = new PatientEntity();
        patient2.setPatientId(2);
        patient2.setPatientName("om");
        patient2.setPatientDelete(false);

        List<PatientEntity> expectedPatients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAllSoftDeleted()).thenReturn(expectedPatients);

  
        List<PatientEntity> result = patientService.fetchallPetients();

      
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedPatients, result);

        // Verify that patient1 is in the result
   //     assertTrue(result.stream().anyMatch(p -> p.getPatientId() == 1 && "Aniket".equals(p.setPatientNamet())));

        // Verify that patient2 is in the result
  //      assertTrue(result.stream().anyMatch(p -> p.getPatientId() == 2 && "om".equals(p.setPatientName())));

        // Verify that all patients are not soft-deleted
        assertTrue(result.stream().noneMatch(PatientEntity::isPatientDelete));

        verify(patientRepository, times(1)).findAllSoftDeleted();
    }

    // Fetch all patient Empty
    @Test
    void testFetchAllPatients_EmptyList() {
     
        when(patientRepository.findAllSoftDeleted()).thenReturn(List.of());

      
        List<PatientEntity> result = patientService.fetchallPetients();

        
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(patientRepository, times(1)).findAllSoftDeleted();
    }
    
    
    
//    // delete patient  // Delete by ID test case
//    @Test
//    void testDeletePatient() {
//      
//        int patientId = 1;
//        doNothing().when(patientRepository).deleteById(patientId);
//
//       
//        assertDoesNotThrow(() -> patientService.deletePatient(patientId));
//
//        
//        verify(patientRepository, times(1)).deleteById(patientId);
//    }
   
    
//    // exception throw     // Delete by ID test case
//    @Test
//    void testDeletePatientException() {
//        
//        int patientId = 1;
//        doThrow(new RuntimeException("Patient not found")).when(patientRepository).deleteById(patientId);
//
//      
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            patientService.deletePatient(patientId);
//        });
//
//        assertEquals("Patient not found", exception.getMessage());
//        verify(patientRepository, times(1)).deleteById(patientId);
//    }
    
    
    // Update Patient Successfully
    @Test
    void updatePatient_Success() {
 
        int patientId = 1;
        PatientEntity existingPatient = new PatientEntity();
        existingPatient.setPatientId(patientId);
        existingPatient.setPatientName("Aniket");
        existingPatient.setPatientAge(30);
        existingPatient.setPatientAddress("mumbai");

        PatientEntity updatedPatientInfo = new PatientEntity();
        updatedPatientInfo.setPatientName("Om");
        updatedPatientInfo.setPatientAge(31);
        updatedPatientInfo.setPatientAddress("pune");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(existingPatient);

        PatientEntity result = patientService.updatePatient(patientId, updatedPatientInfo);

        assertNotNull(result);
        assertEquals(updatedPatientInfo.getPatientName(), result.getPatientName());
        assertEquals(updatedPatientInfo.getPatientAge(), result.getPatientAge());
        assertEquals(updatedPatientInfo.getPatientAddress(), result.getPatientAddress());

        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(existingPatient);
    }

    // update Patient By id But Id is Not Found
    @Test
    void updatePatient_PatientNotFound() {
      
        int patientId = 1;
        PatientEntity updatedPatientInfo = new PatientEntity();
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

  
        assertThrows(IdNotFoundException.class, () -> patientService.updatePatient(patientId, updatedPatientInfo));
        verify(patientRepository).findById(patientId);
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }
 
    //update Patient Id not found Exception
    @Test
    void updatePatientException() {
       
        int patientId = 1;
        PatientEntity updatedPatientInfo = new PatientEntity();
        when(patientRepository.findById(patientId)).thenThrow(new RuntimeException("Something is wrong "));

    
        assertThrows(SomeThingWentWrongException.class, () -> patientService.updatePatient(patientId, updatedPatientInfo));
        verify(patientRepository).findById(patientId);
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }
    

    @Test
    void softDelete_ExistingPatient_ShouldSoftDeleteSuccessfully() {
      
        Integer patientId = 1;
        PatientEntity patient = new PatientEntity();
        patient.setPatientId(patientId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

    
        patientService.softDelete(patientId);

        assertTrue(patient.isPatientDelete());
        verify(patientRepository).save(patient);

    }
 
    // chech patient altready deleted 
    @Test
    void softDeleteIdNotFoundException() {
       
        Integer patientId = 1;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

      
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, 
            () -> patientService.softDelete(patientId));
        
        assertEquals("petient not found id number is : " + patientId, exception.getMessage());
   
    }

}
