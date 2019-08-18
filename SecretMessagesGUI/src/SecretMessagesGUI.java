import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SecretMessagesGUI extends JFrame {
	
	private JTextField tiKey;
	private JTextArea taIn;
	private JTextArea taOut;
	

	public static void main(String[] args) {
		JFrame gui = new SecretMessagesGUI();
		gui.setSize(600,400);
		gui.setVisible(true);
	}
	
	
	public SecretMessagesGUI() {
		setTitle("Todd's Secrete Message App!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		taIn = new JTextArea();
		taIn.setToolTipText("Input Message");
		taIn.setBounds(12, 0, 569, 127);
		getContentPane().add(taIn);
		
		taOut = new JTextArea();
		taOut.setToolTipText("Decoded Message");
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
		btnEncodeDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					String plaintext = taIn.getText();
					int key = Integer.parseInt(tiKey.getText());
					
					String ciphertext = caesar(plaintext, key);
					
					ciphertext = stringReverse(ciphertext);
					
					taOut.setText(ciphertext);
				} catch( Exception e ) {
					//what to do?
				}
			}
		});
		btnEncodeDecode.setBounds(415, 168, 166, 27);
		getContentPane().add(btnEncodeDecode);
	}
	

	private static String stringReverse( String str ) {
		String rts = "";
		for( int l = str.length()-1; l >= 0; l-- ) {
			rts += str.charAt(l);
		}
		return rts;
	}//stringReverse/
	
	
	private static String caesar( String str, int keyVal ) {
		char key = (char) keyVal;
		String csr = "";
		for( int i = 0; i < str.length(); i++ ) {
			char input = str.charAt(i);
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
			csr += input;
		}
		
		return csr;
	}//caesar/

}
