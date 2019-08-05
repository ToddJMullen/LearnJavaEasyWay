import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuessingGame extends JFrame {
	
	private JTextField tiGuess;

	private JLabel lblPrompt;
	private JLabel lblOutput;
	
	private int theNumber;
	private int level = 1;
	


	public static void main(String[] args) {
		GuessingGame game = new GuessingGame();
		game.newGame();
		game.setSize(new Dimension(450,300) );
		game.setVisible(true);
	}//main

	
	public GuessingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Todd's Guessing Game");
		getContentPane().setLayout(null);
		
		JLabel lblToddGuessingGame = new JLabel("Todd's # Guessing Game");
		lblToddGuessingGame.setFont(new Font("Montserrat Light", Font.BOLD | Font.ITALIC, 17));
		lblToddGuessingGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblToddGuessingGame.setBounds(12, 39, 420, 15);
		getContentPane().add(lblToddGuessingGame);
		
		lblPrompt = new JLabel("Guess a number between 1 & 10");
		lblPrompt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrompt.setBounds(12, 93, 251, 15);
		getContentPane().add(lblPrompt);
		
		tiGuess = new JTextField();
		tiGuess.setBounds(308, 91, 40, 19);
		getContentPane().add(tiGuess);
		tiGuess.setColumns(10);
		
		JButton btnGuess = new JButton("Guess!");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		btnGuess.setBounds(178, 147, 114, 25);
		getContentPane().add(btnGuess);
		
		lblOutput = new JLabel("Enter a number above & click Guess!");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(12, 211, 420, 46);
		getContentPane().add(lblOutput);
	}
	
	public void newGame() {
		theNumber = (int)(Math.random() * Math.pow(10, level) + 1);
	}//newGame/
	
	
	public void checkGuess() {
		String guessText = tiGuess.getText();
		String msg = "";
		int guess = Integer.parseInt(guessText);
		

		if( guess == theNumber ) {
			msg = "Correct, the number is " + theNumber + "!"
					+ " Keep going. Guess the new #!"
					;
//					+ "\nYou guessed right in " + guesses + " guesses!";
			newGame();
		} 
		else if( guess > theNumber ) {
			msg = guess + " is too high. Try again.";
		}
		else {
			msg = guess + " is too low. Try again.";
		}
		lblOutput.setText(msg);
		
	}//checkGuess/
	
}
