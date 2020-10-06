package com.hameedu.clinicals.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hameedu.clinicals.model.ClinicalData;
import com.hameedu.clinicals.model.Patient;
import com.hameedu.clinicals.repository.PatientRepository;
import com.hameedu.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	private PatientRepository patientRepository;
	
	Map<String, String> filter = new HashMap<String, String>();
	
	@Autowired
	public PatientController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	@GetMapping(value="/patients")
	public List<Patient> getPatients() {
		return this.patientRepository.findAll();
	}
	
	@GetMapping(value="/patient/{id}")
	public Patient getPatient(@PathVariable("id") int id) {
		return this.patientRepository.findById(id).get();
	}
	
	@PostMapping(value="/patient")
	public Patient savePatient(@RequestBody Patient patient) {
		return this.patientRepository.save(patient);
	}
	
	@GetMapping(value="/patient/analyse/{id}")
	public Patient analyse(@PathVariable("id") int id) {
		Patient patient = this.patientRepository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();		
		
		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<ClinicalData>(clinicalData);
		
		for (ClinicalData data : duplicateClinicalData) {
			if(filter.containsKey(data.getComponentName())) {
				clinicalData.remove(data);
				continue;
			}
			else {
				filter.put(data.getComponentName(), null);
			}
			
			BMICalculator.calculateBMI(clinicalData, data);
		}
		filter.clear();
		return patient;
	}

	
}
