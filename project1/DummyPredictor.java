package project1;

import java.util.ArrayList;

public class DummyPredictor extends Predictor {
	
	@Override
	public ArrayList<DataPoint> readData(String filename) {
		System.out.println("Reading data from " + filename);
		return null;
	}

	@Override
	public String test(DataPoint data) {
		if(data.isTest()) {
			System.out.println("Test data point confirmed!");
		} else {
			System.out.println("NOT a test data point");
		}
		return null;
	}

	@Override
	public Double getAccuracy(ArrayList<DataPoint> data) {
		System.out.println("Checking for accuracy!");
		return 100.0;
	}

	@Override
	public Double getPrecision(ArrayList<DataPoint> data) {
		System.out.println("Checking for precision!");
		return 100.0;
	}

}
