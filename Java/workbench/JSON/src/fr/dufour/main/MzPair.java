package fr.dufour.main;

public class MzPair {

	private Double mz0;
	private Double mz1;
	
	public MzPair(Double mz0, Double mz1) {
		this.mz0 = mz0;
		this.mz1 = mz1;
	}
	
	public Double getMz0() {
		return this.mz0;
	}
	
	public Double getMz1() {
		return this.mz1;
	}
	
	public String toString() {
		return "MzPair: " + mz0 + "," + mz1;
	}
}