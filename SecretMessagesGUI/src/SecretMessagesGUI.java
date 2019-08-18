import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class SecretMessagesGUI extends JFrame {
	private JTextField tiKey;
	public SecretMessagesGUI() {
		setTitle("Todd's Secrete Message App!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JTextArea taIn = new JTextArea();
		taIn.setBounds(12, 0, 569, 127);
		getContentPane().add(taIn);
		
		JTextArea taOut = new JTextArea();
		taOut.setBounds(12, 207, 569, 149);
		getContentPane().add(taOut);
		
		tiKey = new JTextField();
		tiKey.setBounds(524, 139, 57, 27);
		getContentPane().add(tiKey);
		tiKey.setColumns(10);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setBounds(480, 139, 37, 27);
		getContentPane().add(lblKey);
		
		JButton btnEncodeDecode = new JButton("Encode / Decode");
		btnEncodeDecode.setBounds(415, 168, 166, 27);
		getContentPane().add(btnEncodeDecode);
	}

	public static void main(String[] args) {

	}
}
