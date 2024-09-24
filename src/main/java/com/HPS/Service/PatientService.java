package com.HPS.Service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HPS.Entity.PatientEntity;
import com.HPS.Exception.IdNotFoundException;
import com.HPS.Exception.SomeThingWentWrongException;
import com.HPS.Repository.PatientRepository;

@Service
public class PatientService {
	
	private static final Logger logger = Logger.getLogger(PatientService.class);
	
	@Autowired
	private PatientRepository patientrepository;
	
	
	// Insert Patient data 
    public PatientEntity savePatient(PatientEntity patiententity) {
    	 logger.info("Save new patient  "+patiententity.getPatientName());
    	
        return patientrepository.save(patiententity);

    }
    
    
    // Fetch Patient By Id
	 public  Optional<PatientEntity> fetchPentient( int patientId) { 
		 logger.info("Fetching patient with id "+ patientId);		 
		return patientrepository.findByPatientIdAndPatientDeleteFalse(patientId);
		
	 }
	 
    
	 //Fetch all Patient Data
	public List<PatientEntity> fetchallPetients() {
		
		logger.info("Fetching all non-deleted patients");
		
		 return patientrepository.findAllSoftDeleted();
		
	}
	
  
//	// Detete Patient Data
//	public  void deletePatient(int patientId) {
//		 patientrepository.deleteById(patientId);
//		 logger.info("Patient deleted successfully");			
//	}
// 
	
	// Update Patient 
	public PatientEntity  updatePatient(int patientId,PatientEntity patiententity) {
		PatientEntity patient=null	;
        try {
             patient = patientrepository.findById(patientId)
                    .orElseThrow(() -> new IdNotFoundException("Patient not found with id: " + patientId));
            patient.setPatientAddress(patiententity.getPatientAddress());
            patient.setPatientAge(patiententity.getPatientAge());
            patient.setPatientName(patiententity.getPatientName());
         
            PatientEntity updatedPatient = patientrepository.save(patient);
            logger.info("Patient updated successfully");
            return updatedPatient;
         
            
        } catch (IdNotFoundException e) {
	    	  	throw new IdNotFoundException(e.getMessage());
		}
        
        catch (Exception e) {
			 throw new SomeThingWentWrongException("Somthing is Wrong");
			 
        }
		
	}
	
	
	// Delete patient But store soft copy in DB
	public void softDelete(Integer patientId)  {
        Optional<PatientEntity> Optional = patientrepository.findById(patientId);
        if (Optional.isPresent()) {
        	logger.info(Optional +"  ID is present ");
        	PatientEntity patient = Optional.get();
        	patient.setPatientDelete(true);
        	patientrepository.save(patient);
            logger.info("Patient soft deleted successfully: "+ patientId);
        } else {
        	logger.warn("Patient not found for soft delete id: "+ patientId);
            throw new IdNotFoundException("petient not found id number is : " + patientId);
        }
	}
	

}
