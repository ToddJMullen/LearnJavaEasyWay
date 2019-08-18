
import java.util.Scanner;

public class SecretMessages {

	public static void main(String[] args) {
		
		Scanner cli = new Scanner(System.in);
		
		System.out.println("Enter a message to encode or decode:");
		
		String str = cli.nextLine();
		
		String output = "";
		System.out.println("Enter a secret key (-25 - 25)");
		int keyVal = Integer.parseInt(cli.nextLine());
		char key = (char) keyVal;
		
		for( int idx = 0; idx < str.length(); idx++ ) {
			
			char input = str.charAt(idx);

			if( input >= 'A' && input <= 'Z' ) {
				input += key;
				
				if( input > 'Z' ) {
					input -= 26;//shift it back into the uppercase range
				}
				if( input < 'A' ) {
					input += 26;
				}
				
			} else if( input >= 'a' && input <= 'z' ) {
				input += key;
				
				if( input > 'z' ) {
					input -= 26;//shift it back into the lowercase range
				}
				if( input < 'a' ) {
					input += 26;
				}
			} else if( input >= '0' && input <= '9' ) {
				input += (keyVal % 10);
				if( input > '9' ) {
					input -= 10;
				}
				else if( input < '0' ) {
					input += 10;
				}
			}
			output += input;
		}
		
		System.out.println("Output:\n" + output);
		cli.close();

	}//main

}//SecretMessages
