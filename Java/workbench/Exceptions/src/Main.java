
public class Main {

	public static void main(String[] args) {
		
		int a= 2, b = 0;
		
		try {
			System.out.println(a/b);
		} catch (ArithmeticException e){
			System.out.println("Impossible de diviser par 0 "+e.getMessage());
		} finally {
			System.out.println("Execution quand même");
		}
		
System.out.println("Hello");
	}

}
