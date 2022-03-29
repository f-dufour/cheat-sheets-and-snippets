import java.util.Hashtable;
import java.util.Enumeration;

public class Main {

	public static void main(String[] args) {
		
		
		Hashtable<String, Double> ht = new Hashtable<>();
		ht.put("0B0A", 476.3);
		ht.put("1B0A", 490.3);
		ht.put("0B1A", 504.3);
		ht.put("1B1A", 518.3);
		
		Enumeration e = ht.elements();

	    while(e.hasMoreElements())
	      System.out.println(e.nextElement());
		System.out.println(ht.keys());
	}

}