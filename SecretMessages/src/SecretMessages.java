
import java.util.Scanner;

public class SecretMessages {

	public static void main(String[] args) {
		
		Scanner cli = new Scanner(System.in);
		String str;
		String output;
		int keyVal;
		char key;
		
		
		System.out.println("Enter a message to encode or decode:");
		do {
			
			str = cli.nextLine();
			if( str.length() == 0 ) {
				System.out.println(
					"\n\n///////////////////////////////////\n\n"
					+ " So long & thanks for all the fish. "
					+ "\n\n///////////////////////////////////\n\n"
				);
				break;
			}
			key = ' ';
			keyVal = 0;
			
			while( keyVal == 0 ) {
				
				try {
					System.out.println("Enter a secret key (-25 & 25)");
					keyVal = Integer.parseInt(cli.nextLine());
					key = (char) keyVal;
					
				} catch( Exception e ) {
					System.out.println("Numbers Only!");
					
				}
			}

			
			output = "";
			str = stringReverse(str);
			
			for( int idx = 0; idx < str.length(); idx++ ) {
				
				char input = str.charAt(idx);

				output += encode( input, keyVal );
			}

			System.out.println("Output: "
				+ "//////////////////////////////\n\n"
				+ output
				+ "\n\n//////////////////////////////\n"
			);
			
			System.out.println("Enter your next message to encode/decode another message.\nPress ENTER to exit.");

		} while ( str.length() > 0 );
		
		cli.close();

	}//main/
	
	private static String stringReverse( String str ) {
		String rts = "";
		for( int l = str.length()-1; l >= 0; l-- ) {
			rts += str.charAt(l);
		}
		return rts;
	}//stringReverse/
	
	
	private static char encode( char input, int keyVal ) {
		char key = (char) keyVal;
		
		if( input >= 'A' && input <= 'Z' ) {
			input += key;
			
			if( input > 'Z' ) {
				input -= 26;//shift it back into the uppercase range
			}
			if( input < 'A' ) {
				input += 26;
			}
			
		} 
		else if( input >= 'a' && input <= 'z' ) {
			input += key;
			
			if( input > 'z' ) {
				input -= 26;//shift it back into the lowercase range
			}
			if( input < 'a' ) {
				input += 26;
			}
		} 
		else if( input >= '0' && input <= '9' ) {
			input += (keyVal % 10);
			if( input > '9' ) {
				input -= 10;
			}
			else if( input < '0' ) {
				input += 10;
			}
		}
		return input;
	}//encode/

}//SecretMessages
