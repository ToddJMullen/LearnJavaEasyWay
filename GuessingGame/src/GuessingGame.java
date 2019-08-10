import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class GuessingGame extends JFrame {
	
	private JTextField tiGuess;

	private JLabel lblPrompt;
	private JLabel lblOutput;
	private JLabel lblNumTries;
	
	private int theNumber;
	private int level = 1;
	private int attempts = 0;


	public static void main(String[] args) {
		GuessingGame game = new GuessingGame();
		game.newGame();
		game.setSize(new Dimension(500,300) );
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
		tiGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//apparently only fires for ENTER?
				checkGuess();
			}
		});
		tiGuess.setBounds(308, 91, 40, 19);
		getContentPane().add(tiGuess);
		tiGuess.setColumns(10);
		
		JButton btnGuess = new JButton("Guess!");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		btnGuess.setBounds(170, 147, 114, 25);
		getContentPane().add(btnGuess);
		
		lblOutput = new JLabel("Enter a number above & click Guess!");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(12, 184, 420, 40);
		getContentPane().add(lblOutput);

		lblNumTries = new JLabel("You guessed in n tries!");
		lblNumTries.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumTries.setVisible(false);
		lblNumTries.setBounds(12, 225, 420, 40);
		getContentPane().add(lblNumTries);
		
		//radio buttons added to give the difficulty selection, abandoned for now
		JRadioButton rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setBounds(20, 62, 85, 23);
		rdbtnEasy.setVisible(false);
		getContentPane().add(rdbtnEasy);
		
		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setBounds(125, 62, 85, 23);
		rdbtnMedium.setVisible(false);
		getContentPane().add(rdbtnMedium);
		
		JRadioButton rdbtnHard = new JRadioButton("Hard");
		rdbtnHard.setBounds(230, 62, 85, 23);
		rdbtnHard.setVisible(false);
		getContentPane().add(rdbtnHard);
		
		JRadioButton rdbtnCrazy = new JRadioButton("Crazy");
		rdbtnCrazy.setBounds(335, 60, 85, 23);
		rdbtnCrazy.setVisible(false);
		getContentPane().add(rdbtnCrazy);
		
	}
	
	public void newGame() {
		attempts = 0;
		theNumber = (int)(Math.random() * Math.pow(10, level) + 1);
	}//newGame/
	
	
	public void checkGuess() {
		String guessText = tiGuess.getText();
		String msg = "";
		try {
			attempts++;
			int guess = Integer.parseInt(guessText);
			
			if( guess == theNumber ) {
				msg = "Correct, the number is " + theNumber + "!"
//						+ " Keep going. Guess the new number!"
						;
				String tries = attempts == 1 ? " try!" : " tries!";
				lblNumTries.setText("\nYou guessed right in " + attempts + tries);
				lblNumTries.setVisible(true);
				newGame();
			} 
			else if( guess > theNumber ) {
				msg = guess + " is too high. Try again.";
			}
			else {
				msg = guess + " is too low. Try again.";
			}
		} catch( Exception e ) {
			msg = "Enter a whole number between 1 and " + (int)Math.pow(10, level);
		} finally {
			
			lblOutput.setText(msg);
			tiGuess.requestFocus();
			tiGuess.selectAll();
		}
		
		
	}//checkGuess/
}
