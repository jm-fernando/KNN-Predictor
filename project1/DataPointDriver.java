package project1;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class DataPointDriver {

	private static double accuracy = 0.0;
	private static double precision = 0.0;
	
	public static void main(String[] args) {
		
		String testdata = "C:/Users/jerom/Desktop/trainingvalues.txt";
		DataPoint dataPointOne = new DataPoint (1.5, 2.6, "Good", true);
		DataPoint dataPointTwo = new DataPoint (3.5, 4.0, "Bad", false);
		
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
		data.add(dataPointOne);
		data.add(dataPointTwo);
		
		DummyPredictor dPredictor = new DummyPredictor();
		
		dPredictor.readData(testdata);
		dPredictor.test(dataPointOne);
		dPredictor.test(dataPointTwo);
		accuracy = dPredictor.getAccuracy(data);
		precision = dPredictor.getPrecision(data);
		
		
		
		SwingUtilities.invokeLater(
				new Runnable() { public void run() { initAndShowGUI(); } }
				);
	}
	
	private static void initAndShowGUI() {
		JFrame frame = new JFrame("Data Point Testing");
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2,2));
		
		contentPane.add(new JButton("Accuracy: " + accuracy));
		contentPane.add(new JButton("Precision: " + precision));
		
		frame.pack();
		frame.setVisible(true);
	}
}
