
import java.util.Scanner;

public class SecretMessages {

	public static void main(String[] args) {
		
		Scanner cli = new Scanner(System.in);
		
		System.out.println("Enter a message to encode or decode:");
		
		String str = cli.nextLine();
		
		String output = "";
		char key = 13;
		
		for( int idx = 0; idx < str.length(); idx++ ) {
			
			char input = str.charAt(idx);
			
			if( input >= 'A' && input <= 'Z' ) {
				input += key;
				
				if( input > 'Z' ) {
					input -= 26;//shift it back into the uppercase range
				}
				
			}
			output += input;
		}
		
		System.out.println("Output:\n" + output);

	}//main

}//SecretMessages
