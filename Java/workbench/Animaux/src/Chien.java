public class Chien extends Canin implements Rintintin {

	public Chien() {

	}

	public Chien(String couleur, int poids) {
		this.couleur = couleur;
		this.poids = poids;
	}

	void crier() {
		System.out.println("J'aboie sans raison !");
	}

	/*
	 * From interface Rintintin 
	 */
	
	public void faireCalin() {
		System.out.println("Je te fais un GROS CÂLIN");
	}

	public void faireLeBeau() {
		System.out.println("Je fais le beau !");
	}

	public void faireLechouille() {
		System.out.println("Je fais de grosses léchouilles...");
	}
}