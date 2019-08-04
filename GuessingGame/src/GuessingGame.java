import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GuessingGame extends JFrame {
	private JTextField textField;
	public GuessingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Todd's Guessing Game");
		getContentPane().setLayout(null);
		
		JLabel lblToddGuessingGame = new JLabel("Todd' Guessing Game");
		lblToddGuessingGame.setFont(new Font("Montserrat Light", Font.BOLD | Font.ITALIC, 17));
		lblToddGuessingGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblToddGuessingGame.setBounds(12, 39, 420, 15);
		getContentPane().add(lblToddGuessingGame);
		
		JLabel lblGuessANumber = new JLabel("Guess a number between 1 & 100");
		lblGuessANumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGuessANumber.setBounds(12, 93, 251, 15);
		getContentPane().add(lblGuessANumber);
		
		textField = new JTextField();
		textField.setBounds(308, 91, 40, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnGuess = new JButton("Guess!");
		btnGuess.setBounds(178, 147, 114, 25);
		getContentPane().add(btnGuess);
		
		JLabel lblEnterANumber = new JLabel("Enter a number above & click Guess!");
		lblEnterANumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterANumber.setBounds(12, 211, 420, 15);
		getContentPane().add(lblEnterANumber);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
