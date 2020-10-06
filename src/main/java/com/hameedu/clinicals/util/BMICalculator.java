package com.hameedu.clinicals.util;

import java.util.List;

import com.hameedu.clinicals.model.ClinicalData;

public class BMICalculator {
	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData data) {
		if(data.getComponentName().equalsIgnoreCase("hw")){
			String[] heightAndWeight = data.getComponentValue().split("/");
			float height = Float.parseFloat(heightAndWeight[0]) * 0.4356F;
			float bmi = (float) (Float.parseFloat(heightAndWeight[1])/(Math.pow(height, 2)));
			ClinicalData bmiData = new ClinicalData();
			bmiData.setComponentName("bmi");
			bmiData.setComponentValue(Float.toString(bmi));
			
			clinicalData.add(bmiData);
		}
	}
}
