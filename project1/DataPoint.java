package project1;

public class DataPoint {
	
	protected Double f1;
	protected Double f2;
	protected String label;
	protected boolean isTest;
	
	public DataPoint(Double f1, Double f2, String label, Boolean isTest) {
		this.f1 = f1;
		this.f2 = f2;
		this.label = label;
		this.isTest = isTest;
	}
	
	public DataPoint() {
		this.f1 = 0.0;
		this.f2 = 0.0;
		this.label = "";
		this.isTest = false;
	}
	
	public double getF1() {
		return this.f1;
	}
	
	public double getF2() {
		return this.f2;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public boolean isTest() {
		return this.isTest;
	}
	
	public void setF1(Double val) {
		this.f1 = val;
	}
	
	public void setF2(Double val) {
		this.f2= val;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}
	
}
