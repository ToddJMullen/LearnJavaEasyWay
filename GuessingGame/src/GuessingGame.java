import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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
	
	private JButton btnGuess;
	private JButton btnPlayAgain;

	private ButtonGroup group;
	private JRadioButton rdbtnEasy;
	private JRadioButton rdbtnMedium;
	private JRadioButton rdbtnHard;
	private JRadioButton rdbtnCrazy;
	
	private Box boxLevels;
	private Box boxPrompt;
	
	private int max = 1;
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

		boxPrompt = new Box( BoxLayout.X_AXIS );
		boxPrompt.setVisible(true);
		boxPrompt.setBounds(35, 70, 380, 20);
		getContentPane().add(boxPrompt);
		
		lblPrompt = new JLabel();
		lblPrompt.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblPrompt.setBounds(12, 93, 251, 15);
		boxPrompt.add(lblPrompt);
//		getContentPane().add(lblPrompt);
		
		boxPrompt.add( Box.createRigidArea(new Dimension(75,0)) );
		
		tiGuess = new JTextField();
		tiGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//apparently only fires for ENTER?
				checkGuess();
			}
		});
//		tiGuess.setBounds(10, 10, 15, 19);//no effect in box
//		tiGuess.setColumns(3);//no effect in box
//		tiGuess.setPreferredSize(new Dimension(45, 0));//no effect in box
		boxPrompt.add(tiGuess);
//		getContentPane().add(tiGuess);
		
		btnGuess = new JButton("Guess!");
		btnGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkGuess();
			}
		});
		btnGuess.setBounds(170, 145, 114, 25);
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
		boxLevels = new Box( BoxLayout.X_AXIS );
		boxLevels.setBounds(87, 70, 280, 40);
		boxLevels.setVisible(false);
		getContentPane().add(boxLevels);
		group = new ButtonGroup();
//		boxLevels.add(group);// << inconsistent, the group is only a controller for which btn is selected
		
		
		rdbtnEasy = new JRadioButton("Easy", true );
//		rdbtnEasy.setSelected(true);
		rdbtnEasy.setBounds(20, 62, 85, 23);
		rdbtnEasy.setVisible(true);
		group.add(rdbtnEasy);
		boxLevels.add(rdbtnEasy);
//		getContentPane().add(rdbtnEasy);
		
		rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setBounds(125, 62, 85, 23);
		rdbtnMedium.setVisible(true);
		group.add(rdbtnMedium);
		boxLevels.add(rdbtnMedium);
//		getContentPane().add(rdbtnMedium);
		
		rdbtnHard = new JRadioButton("Hard");
		rdbtnHard.setBounds(230, 62, 85, 23);
		rdbtnHard.setVisible(true);
		group.add(rdbtnHard);
		boxLevels.add(rdbtnHard);
//		getContentPane().add(rdbtnHard);
		
		rdbtnCrazy = new JRadioButton("Crazy");
		rdbtnCrazy.setBounds(335, 60, 85, 23);
		rdbtnCrazy.setVisible(true);
		group.add(rdbtnCrazy);
		boxLevels.add(rdbtnCrazy);
//		getContentPane().add(rdbtnCrazy);
		
		btnPlayAgain = new JButton("Play Again!");
		btnPlayAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		btnPlayAgain.setBounds(170, 145, 114, 25);
		btnPlayAgain.setVisible(false);
		getContentPane().add(btnPlayAgain);
		
	}
	
	public void newGame() {
		boxLevels.setVisible(false);
		btnPlayAgain.setVisible(false);
		btnGuess.setVisible(true);
		lblNumTries.setVisible(false);
		lblOutput.setText("Game On!");
		tiGuess.setText("");
		if( rdbtnEasy.isSelected() ) {
			level = 1;
		}
		if( rdbtnMedium.isSelected() ) {
			level = 2;
		}
		if( rdbtnHard.isSelected() ) {
			level = 3;
		}
		if( rdbtnCrazy.isSelected() ) {
			level = 5;
		}
		attempts = 0;
		max = (int)Math.pow(10, level);
		theNumber = (int)(Math.random() * max  + 1);
		lblPrompt.setText("Guess a number between 1 & " + max);
		boxPrompt.setVisible(true);
	}//newGame/
	
	
	public void checkGuess() {
		String guessText = tiGuess.getText();
		String msg = "";
		try {
			attempts++;
			int guess = Integer.parseInt(guessText);
			
			if( guess == theNumber ) {
				msg = "Correct! The number is " + theNumber + "!"
//						+ " Keep going. Guess the new number!"
						;
				String tries = attempts == 1 ? " try!" : " tries!";
				lblNumTries.setText("You guessed right in " + attempts + tries);
				btnGuess.setVisible(false);
				
				lblNumTries.setVisible(true);
				boxPrompt.setVisible(false);
				boxLevels.setVisible(true);
				btnPlayAgain.setVisible(true);
//				newGame();
			} 
			else if( guess > theNumber ) {
				msg = guess + " is too high. Try again.";
			}
			else {
				msg = guess + " is too low. Try again.";
			}
		} catch( Exception e ) {
			msg = "Enter a whole number between 1 and " + max;
		} finally {
			
			lblOutput.setText(msg);
			tiGuess.requestFocus();
			tiGuess.selectAll();
		}
		
		
	}//checkGuess/
}
