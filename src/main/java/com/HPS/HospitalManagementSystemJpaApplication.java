package com.HPS;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementSystemJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementSystemJpaApplication.class, args);
		
		PropertyConfigurator.configure(HospitalManagementSystemJpaApplication.class.getClassLoader().getResource("log4j.properties"));
		System.out.println("Application Started..");
	}

}
