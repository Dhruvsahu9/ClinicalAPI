package com.hameedu.clinicals.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hameedu.clinicals.model.ClinicalData;
import com.hameedu.clinicals.model.ClinicalDataRequest;
import com.hameedu.clinicals.model.Patient;
import com.hameedu.clinicals.repository.ClinicalDataRespository;
import com.hameedu.clinicals.repository.PatientRepository;
import com.hameedu.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

	private ClinicalDataRespository clinicalDataRepository;

	private PatientRepository patientRepository;

	@Autowired
	public ClinicalDataController(
			ClinicalDataRespository clinicalDataRepository,
			PatientRepository patientRepository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepository = patientRepository;
	}

	@PostMapping(value = "/clinicals")
	public ClinicalData saveClinicalData(
			@RequestBody ClinicalDataRequest clinicalDataRequest) {
		Patient patient = this.patientRepository.findById(
				clinicalDataRequest.getId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(clinicalDataRequest.getComponentName());
		clinicalData.setComponentValue(clinicalDataRequest.getComponentValue());
		clinicalData.setPatient(patient);
		return this.clinicalDataRepository.save(clinicalData);
	}

	@GetMapping(value = "/clinicals/{patientId}/{componentName}")
	public List<ClinicalData> getClinicalData(
			@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		if(componentName.equalsIgnoreCase("bmi")) {
			componentName = "hw";
		}
		List<ClinicalData> clinicalData = clinicalDataRepository
				.findByPatientIdAndComponentNameOrderByMeasuredDateTime(
						patientId, componentName);
		
		ArrayList<ClinicalData> duplicateClinicalData = new ArrayList<ClinicalData>(clinicalData);
		
		for (ClinicalData data : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, data);
		}		
		
		return clinicalData;
	}
}
