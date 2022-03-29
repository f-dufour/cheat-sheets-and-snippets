// 
// Celsius <-> Fahrenheit converter
// Florent DUFOUR
// October 2017
// Language = French
//

import java.util.Scanner;

public class Fahrsius {
	public static void main(String[] args) {
	
	Scanner sc = new Scanner(System.in);
	char mode = ' ';
	float Newtemp = 0f;

	// ENTÊTE
	
	System.out.println("CONVERTISSEUR 0.1 \n ***************");
	System.out.println("Quelle conversion effectuer ? \n"
			+ "1. Celsius -> Fahrenheit \n"
			+ "2. Fahrenheit -> Celsius \n");
	
	do{
		System.out.print("Votre choix (1|2): ");
		mode = sc.nextLine().charAt(0);
	}while(mode != '1' && mode != '2');
	
	System.out.print("Température à convertir: ");
	float temp = sc.nextFloat();
	
	// CALCUL
			
	switch(mode) {
		case '1':
		Newtemp = (9.0f/5.0f)*temp+32;
		System.out.println(temp+" °C "+"correspondent à  "+Newtemp+" °F");
			break;
		case '2':
		Newtemp = ((temp-32)*5.0f/9.0f);
		System.out.println(temp+" °F "+"correspondent à  "+Newtemp+" °C");
			break;	
		}
	}
}