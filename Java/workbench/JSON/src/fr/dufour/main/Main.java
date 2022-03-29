package fr.dufour.main;

public class Main {

	public static void main(String[] args) {
		try {
			JsonInit.initJsonParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MzPair beginMz = JsonInit.getMzPair("R1C3", "beginMz");
		MzPair mZCode = JsonInit.getMzPair("R1C3", "mZCode");
		
		System.out.println(mZCode.toString());
		System.out.println(beginMz.toString());

	}
}