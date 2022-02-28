import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

public class Main {

	public static void main(String[] args) {

		String line;

		try {

			// FileReader fr = new FileReader("/Users/Florent/eclipse-workspace-Perso/FlotDonnées/src/Untilted.txt");
			
			URL ff = Main.class.getResource("Untilted.txt");
			
			FileReader fr = new FileReader(ff.getPath());
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			  br.close();   
			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
