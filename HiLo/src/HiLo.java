import java.util.Scanner;
//import java.util.regex.Pattern;

public class HiLo {

	public static void main(String[] args) {
		
		Scanner scan	= new Scanner(System.in);
//		Pattern levels	= Pattern.compile("e|m|h");

		String play		= "";
		String phrase	= "";
		String level	= "";
		
		int theNumber	= 0; 
		int guess		= 0;
		int guesses		= 0;
		int upper		= 10;
		
		
		do {//game loop
			
			System.out.println("Choose a difficulty: Easy (e), Medium (m), Hard (h), Crazy (c)");
			do {
				level = scan.next();
				switch( level ) {
					case "e": upper = 10; break;
					case "m": upper = 100; break;
					case "h": upper = 1000; break;
					case "c": upper = 1000000; break;
					default:
						System.out.println("Unknown level: '" + level + "'"
							+ "\nPlease choose from from Easy (e) medium (m) Hard (h) or Crazy (c)");
				}
			} while( !level.toLowerCase().matches("e|m|h|c") );
			
			
			theNumber = (int)(Math.random() * upper + 1);
			System.out.println("Guess a number from 1 to " + upper + ":");
			
			while( guess != theNumber ) {
				guess = scan.nextInt();
				guesses++;
				
				if( guess == theNumber ) {
					phrase = "//////////////////////////////////"
							+ "\nCorrect, the number is " + theNumber + "!"
							+ "\nYou guessed right in " + guesses + " guesses!";
				} 
				else if( guess > theNumber ) {
					phrase = guess + " is too high\nTry again.";
				}
				else {
					phrase = guess + " is too low\nTry again.";
				}
				
				System.out.println( phrase );
			}//while
			
			System.out.println("Do you want to play again y/n?");
			play = scan.next();
			
		} while ( play.equalsIgnoreCase("y") );
		
		System.out.println("Good! Me neither! Xb");
//		System.out.println("OK. See you next time!");
		scan.close();
		
//		System.out.println( "The number is: " + theNumber );
		
		
	}//main

}//HiLo
