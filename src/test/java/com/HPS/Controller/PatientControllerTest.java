package com.HPS.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.HPS.Entity.PatientEntity;
import com.HPS.Exception.IdNotFoundException;
import com.HPS.Service.PatientService;

public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private PatientEntity testPatient;
    
    public static final String ENTER_NAME = "Please Enter patient Name..";
    public static final String ID_NOT_Found = "Patient id is not present in the system";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPatient = new PatientEntity();
        testPatient.setPatientId(1);
        testPatient.setPatientName("John Doe");
    }

    // test save patient 
    @Test
    void testSavePatient() {
        when(patientService.savePatient(any(PatientEntity.class))).thenReturn(testPatient);

        ResponseEntity<?> response = patientController.savePatient(testPatient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPatient, response.getBody());
    }

    @Test
    void testFetchPatient() {
        when(patientService.fetchPentient(1)).thenReturn(Optional.of(testPatient));

        ResponseEntity<?> response = patientController.fetchPentient(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPatient, response.getBody());
    }

    // Test Fetch All patient
    @Test
    void testFetchAllPatients() {
        List<PatientEntity> patients = Arrays.asList(testPatient);
        when(patientService.fetchallPetients()).thenReturn(patients);

        ResponseEntity<List<PatientEntity>> response = patientController.fetchAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patients, response.getBody());
    }

    // Test Update Patient 
    @Test
    void testUpdatePatient() {
        when(patientService.updatePatient(eq(1), any(PatientEntity.class))).thenReturn(testPatient);

        ResponseEntity<PatientEntity> response = patientController.updatePatient(testPatient, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPatient, response.getBody());
    }
    
	@Test
	void testSoftDeletePatientSuccess() {

		int patientId = 1;
		String expectedResponse = "Patient Data is Deleted -- " + patientId;

		ResponseEntity<String> response = patientController.softDelete(patientId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedResponse, response.getBody());
		verify(patientService, times(1)).softDelete(patientId);
	}
	
	// Delete Patient  By Id 
	@Test
	void testSoftDeletePatientNotFound() {
		int patientId = 1;
//		String expectedResponse = patientId + " patient ID is Not Found ";
		
//		String incorrectResponse = "Incorrect response";
		doThrow(new IdNotFoundException(ID_NOT_Found )).when(patientService).softDelete(patientId);

		ResponseEntity<String> response = patientController.softDelete(patientId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		 assertEquals(ID_NOT_Found , response.getBody());
		verify(patientService, times(1)).softDelete(patientId);
	}


    
    //test Save Patient With empty Name
    @Test
    void testSavePatientNameMissingException() {
        PatientEntity emptyNamePatient = new PatientEntity();
        emptyNamePatient.setPatientName("");

        ResponseEntity<?> response = patientController.savePatient(emptyNamePatient);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ENTER_NAME, response.getBody());
    }

    //test save patient exception
    @Test
    void testSavePatientException() {
        when(patientService.savePatient(any(PatientEntity.class))).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = patientController.savePatient(testPatient);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("somthing is wrong", response.getBody());
    }

    // test fetch patient By Id Throw Id not found exception
    @Test
    void testFetchPatientIdNotFoundException() {
        when(patientService.fetchPentient(99)).thenReturn(Optional.empty());

        ResponseEntity<?> response = patientController.fetchPentient(99);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ID_NOT_Found, response.getBody());
    }

    //test fetch patient exception 
    @Test
    void testFetchPatientException() {
        when(patientService.fetchPentient(1)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = patientController.fetchPentient(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("somthing is wrong", response.getBody());
    }

    //test fetch all patient 
    @Test
    void testFetchAllPatientsEmptyList() {
        when(patientService.fetchallPetients()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = patientController.fetchAllPatients();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }



    //test softDelete IdNotFoundException
    @Test
    void testSoftDeleteIdNotFoundException() {
        doThrow(new IdNotFoundException(ID_NOT_Found))
            .when(patientService).softDelete(99);

        ResponseEntity<?> response = patientController.softDelete(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ID_NOT_Found, response.getBody());
    }
}
