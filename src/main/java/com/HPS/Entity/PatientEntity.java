package com.HPS.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class PatientEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int patientId ;
	private String patientName ;
	private int patientAge ;
	private String patientAddress;
	private boolean patientDelete ;
	
	public PatientEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PatientEntity(int patientId, String patientName, int patientAge, String patientAddress,
			boolean patientDelete) {
		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.patientAddress = patientAddress;
		this.patientDelete = patientDelete;
	}
	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public boolean isPatientDelete() {
		return patientDelete;
	}
	public void setPatientDelete(boolean patientDelete) {
		this.patientDelete = patientDelete;
	}
	@Override
	public String toString() {
		return "PatientEntity [patientId=" + patientId + ", patientName=" + patientName + ", patientAge=" + patientAge
				+ ", patientAddress=" + patientAddress + ", patientDelete=" + patientDelete + "]";
	}

}
