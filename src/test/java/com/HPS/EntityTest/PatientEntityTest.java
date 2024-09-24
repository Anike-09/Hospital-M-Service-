package com.HPS.EntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.HPS.Entity.PatientEntity;

public class PatientEntityTest {
	
    // Test constructor and Getter Methode
    @Test
    void tesConstructorAndGetters() {
        PatientEntity patient = new PatientEntity(1, "Aniket", 30, "Kolhapur", false);
        
        assertEquals(1, patient.getPatientId());
        assertEquals("Aniket", patient.getPatientName());
        assertEquals(30, patient.getPatientAge());
        assertEquals("Kolhapur", patient.getPatientAddress());
        assertFalse(patient.isPatientDelete());
    }

    // test Setter Methode
    @Test
    void testSetters() {
        PatientEntity patient = new PatientEntity();
        
        patient.setPatientId(2);
        patient.setPatientName("Aniket");
        patient.setPatientAge(25);
        patient.setPatientAddress("Kolhapur");
        patient.setPatientDelete(true);
        
        assertEquals(2, patient.getPatientId());
        assertEquals("Aniket", patient.getPatientName());
        assertEquals(25, patient.getPatientAge());
        assertEquals("Kolhapur", patient.getPatientAddress());
        assertTrue(patient.isPatientDelete());
    }

    // Test To string Methode
    @Test
    void testToString() {
        PatientEntity patient = new PatientEntity(3, "Aniket", 40, "Kolhapur", true);
        String expected = "PatientEntity [patientId=3, patientName=Aniket, patientAge=40, patientAddress=Kolhapur, patientDelete=true]";
        assertEquals(expected, patient.toString());
    }

}
