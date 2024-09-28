package com.HPS.Controller;

import static com.HPS.PetientConstant.Constant.errorMessages.ENTER_NAME;
import static com.HPS.PetientConstant.Constant.errorMessages.ID_NOT_Found;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HPS.Entity.PatientEntity;
import com.HPS.Exception.IdNotFoundException;
import com.HPS.Exception.NameMissingException;
import com.HPS.Service.PatientService;

@RestController
@RequestMapping("/api")
public class PatientController {

	private static final Logger logger = Logger.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;

	// Save Data
	@PostMapping("/patients")
	public ResponseEntity<?> savePatient(@RequestBody PatientEntity patiententity) {

		try {
			if (!patiententity.getPatientName().isEmpty()) {
				logger.info("Save Patient Process");
				PatientEntity savedPatient = patientService.savePatient(patiententity);
				return ResponseEntity.ok(savedPatient);
			} else {
				logger.error(ENTER_NAME);
				throw new NameMissingException(ENTER_NAME);
			}
		} catch (NameMissingException e) {
			return ResponseEntity.badRequest().body(e.getErrMsg());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("somthing is wrong");
		}

	}

	// Fetch Data by Id
	@GetMapping("patients/{patientId}")
	public ResponseEntity<?> fetchPentient(@PathVariable int patientId) {

		try {
			Optional<PatientEntity> patient = patientService.fetchPentient(patientId);
			if (patient.isPresent()) {
				logger.info("Patient found with id  " + patientId);

				return ResponseEntity.ok(patient.get());
			} else {
				logger.warn("Patient not found with id:" + patientId);
				throw new IdNotFoundException(ID_NOT_Found);
			}
		} catch (IdNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("somthing is wrong");
		}
	}

	// Fetch All Data
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/patients/demo")
	public ResponseEntity<List<PatientEntity>> fetchAllPatients() {
		logger.info("Fetching all patients");
		List<PatientEntity> patients = patientService.fetchallPetients();
		if (patients.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(patients);
	}

//	// Delete Data from Id
//	@DeleteMapping("/patients/{patientId}")
//	public ResponseEntity<String> deletePatient(@PathVariable int patientId) {
//
//		try {
//			patientService.softDelete(patientId);
//			logger.error("Patient Data is Deleted  -- " + patientId);
//			return ResponseEntity.ok("Patient Data is Deleted  -- " + patientId);
//		} catch (IdNotFoundException ex) {
//			logger.warn(patientId + "patient is Not Found ");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(patientId + "  patient ID is Not Found ");
//		}
//
//	}

	// Update Patient By ID
	@PutMapping("/patients/{patientId}")
	public ResponseEntity<PatientEntity> updatePatient(@RequestBody PatientEntity patiententity,
			@PathVariable int patientId) {
		logger.info("Updating patient with id " + patientId);

		PatientEntity patientEntity = patientService.updatePatient(patientId, patiententity);

		logger.debug("Updateed...");
		return ResponseEntity.status(HttpStatus.OK).body(patientEntity);
	}

	// Delete Patient By ID
	@DeleteMapping("/patients/patientsSoft/{patientId}")
	public ResponseEntity<String> softDelete(@PathVariable Integer patientId) {

		try {
			patientService.softDelete(patientId);
			logger.info("Patient Data is Deleted  -- " + patientId);
			return ResponseEntity.ok("Patient Data is Deleted  -- " + patientId);
		} catch (IdNotFoundException ex) {
			logger.warn(patientId + "patient is Not Found ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ID_NOT_Found);
		}

	}

}
