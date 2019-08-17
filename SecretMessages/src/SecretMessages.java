
import java.util.Scanner;

public class SecretMessages {

	public static void main(String[] args) {
		
		Scanner cli = new Scanner(System.in);
		
		System.out.println("Enter a message to encode or decode:");
		
		String str = cli.nextLine();
		
		String output = "";
		
		for( int idx = str.length()-1; idx >= 0; idx-- ) {
			output += str.charAt(idx);
		}
		
		System.out.println("Output:\n" + output);

	}//main

}//SecretMessages
