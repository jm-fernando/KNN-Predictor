package project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;


public class KNNPredictor extends Predictor {

	private int k;
	private int lineCount = 0;
	private int passDead = 0;
	private int passAlive = 0;
	private int extraData = 0;
	private int trainData = 0;
	private int deadOrAliveInt = 0;
	private int dataTotal = 0;
	private int deadTotal = 0;
	private int aliveTotal = 0;
	private int truePositive = 0;
	private int falsePositive = 0;
	private int trueNegative = 0;
	private int falseNegative = 0;
	private double passAgeDbl = 0.0;
	private double passFareDbl = 0.0;
	private double totalDistance = 0.0;
	private double label = 0.0;
	private double survivorCount = 0.0;
	private double distance = 0.0;
	private double labelPoint = 0.0;
	private double precisionCount = 0.0;
	private double total = 0.0;
	private double precision = 0.0;
	private double accuracy = 0.0;
	private boolean isTest;
	private String passAge;
	private String passFare;
	private String deadOrAlive;
	
	private ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();
	
	public KNNPredictor(int k) {
		this.k = k;
	}

	
	private List<String> getRecordFromLine(String line){
		List<String> values = new ArrayList<String>();
		try(Scanner rowScanner  = new Scanner(line)){
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}

	@Override
	ArrayList<DataPoint> readData(String filename) {
		File csvfile = new File (filename);
		ArrayList<DataPoint> dataList2 = new ArrayList<DataPoint>();
		try {
			Scanner scanner = new Scanner(csvfile);
			while(scanner.hasNextLine()) {
				lineCount++;
				List<String> passData = getRecordFromLine(scanner.nextLine());
				
				if (passData.size() < 7) {
					extraData++;
					continue;
				}
				
				passAge = passData.get(5);
				passFare = passData.get(6);
				deadOrAlive = passData.get(1);
				
				
				try {
					passAgeDbl = Double.parseDouble(passAge);
				} catch(NumberFormatException ex) {
					continue;
				}
				try {
					passFareDbl = Double.parseDouble(passFare);
				} catch(NumberFormatException ex) {
					continue;
				}
				try {
					deadOrAliveInt = Integer.parseInt(deadOrAlive);
				} catch(NumberFormatException ex) {
					continue;
				}
				
				
				
				if(splitToTestOrTrain()){
					if (deadOrAliveInt == 1) {
						passAlive++;
					} else if(deadOrAliveInt == 0) {
						passDead++;
					}
				}
				DataPoint dataNeeded = new DataPoint(passAgeDbl, passFareDbl, deadOrAliveInt, splitToTestOrTrain());
				dataList2.add(dataNeeded);
			}	
			scanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found :(");
		}
		return dataList2;
	}
	
	
	
	public boolean splitToTestOrTrain() {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		
		if (randNum < 0.9) {
			isTest = true;
		} else {
			isTest = false;
		}
		return isTest;
	}

		
	
	@Override
	String test(DataPoint data) {
		
		trainData = passAlive + passDead;
		
		Double[][] arr = new Double [trainData][2];
		
		for (int r = 0; r < trainData; r++) {
			DataPoint training = dataList.get(r);
			totalDistance = getDistance(data, training);
			label = training.getLabel();
			arr[r][0] = totalDistance;
			arr[r][1] = label;
		}
		
		java.util.Arrays.sort(arr, new java.util.Comparator<Double[]>() {
			public int compare(Double[] a, Double[] b) {
				return a[0].compareTo(b[0]);
			}
		});
		
		for (int r = 0; r < k; r++) {
			survivorCount = arr[r][1];
			if (survivorCount == 1) {
				aliveTotal++;
			} else if (survivorCount == 0) {
				deadTotal++;
			}
		}
		if (aliveTotal > deadTotal) {
			return "1";
		} else {
			return "0";
		}
	}
	
	
	private double getDistance(DataPoint x, DataPoint y) {
		
		distance = Math.sqrt(Math.pow(y.getF2() - x.getF2(), 2) + Math.pow(y.getF1() - x.getF1(), 2));
		return distance;
	}

	@Override
	Double getAccuracy(ArrayList<DataPoint> data) {
		for(DataPoint dataPoint : data) {
			labelPoint = dataPoint.getLabel();
			dataList = data;
			precisionCount = Double.parseDouble(test(dataPoint));
			
			if(labelPoint == 1.0) {
				if(precisionCount == 1.0) {
					truePositive++;
				} else if (precisionCount == 0.0) {
					falsePositive++;
				}
			}
			
			if(labelPoint == 0.0) {
				if (precisionCount == 0.0) {
					trueNegative++;
				} else if (precisionCount == 0.0) {
					falseNegative++;
				}
			}
		}
		
		total = truePositive + falsePositive + trueNegative + falseNegative;
		accuracy = (truePositive + trueNegative) / total;
		return accuracy;
	}

	@Override
	Double getPrecision(ArrayList<DataPoint> data) {
		
		for(DataPoint dataPoint : data) {
			labelPoint = dataPoint.getLabel();
			dataList = data;
			precisionCount = Double.parseDouble(test(dataPoint));
			
			if(labelPoint == 1.0) {
				if(precisionCount == 1.0) {
					truePositive++;
				} else if (precisionCount == 0.0) {
					falsePositive++;
				}
			}
			
			if(labelPoint == 0.0) {
				if (precisionCount == 0.0) {
					trueNegative++;
				} else if (precisionCount == 0.0) {
					falseNegative++;
				}
			}
		}
		
		total = truePositive + falsePositive + trueNegative + falseNegative;
		precision = (truePositive + trueNegative) / total;
		return precision;
	}
}
