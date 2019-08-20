import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollPane;

public class SecretMessagesGUI extends JFrame {
	
	/**
	 * serialVersionUID not needed here, but added to remove warning
	 */
	private static final long serialVersionUID = 1L;

	private static String DEFAULT_KEY = "3";
	
	private JSlider		slKey;
	
	private JTextField	tiKey;
	
	private JTextArea	taIn;
	private JTextArea	taOut;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	

	public static void main(String[] args) {
		JFrame gui = new SecretMessagesGUI();
		gui.setSize(600,500);
		gui.setVisible(true);
	}
	
	
	public SecretMessagesGUI() {
		getContentPane().setBackground(Color.DARK_GRAY);
		setTitle("Todd's Secret Message App!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 0, 569, 163);
		getContentPane().add(scrollPane);
		
		taIn = new JTextArea();
		scrollPane.setViewportView(taIn);
		taIn.setLineWrap(true);
		taIn.setWrapStyleWord(true);
		taIn.setBackground(Color.GRAY);
		taIn.setFont(new Font("Comfortaa", Font.PLAIN, 20));
		taIn.setMargin(new Insets(3, 3, 3, 3));
		taIn.setToolTipText("Input Message");
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 294, 569, 163);
		getContentPane().add(scrollPane_1);
		
		taOut = new JTextArea();
		scrollPane_1.setViewportView(taOut);
		taOut.setLineWrap(true);
		taOut.setWrapStyleWord(true);
		taOut.setBackground(Color.GRAY);
		taOut.setFont(new Font("Comfortaa", Font.BOLD, 20));
		taOut.setMargin(new Insets(3, 3, 3, 3));
		taOut.setToolTipText("Decoded Message");
		
		tiKey = new JTextField();
		tiKey.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tiKey.selectAll();
			}
		});
		tiKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = Integer.parseInt(tiKey.getText());
				slKey.setValue(key);
			}
		});
		tiKey.setText(DEFAULT_KEY);
		tiKey.setBackground(Color.LIGHT_GRAY);
		tiKey.setFont(new Font("Comfortaa", Font.PLAIN, 20));
		tiKey.setMargin(new Insets(3, 3, 3, 3));
		tiKey.setBounds(522, 175, 57, 38);
		getContentPane().add(tiKey);
		tiKey.setColumns(10);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setFont(new Font("Comfortaa", Font.PLAIN, 18));
		lblKey.setForeground(Color.WHITE);
		lblKey.setBounds(474, 183, 37, 27);
		getContentPane().add(lblKey);
		
		JButton btnEncodeDecode = new JButton("Encode / Decode");
		btnEncodeDecode.setFont(new Font("Comfortaa", Font.PLAIN, 22));
		btnEncodeDecode.setForeground(Color.WHITE);
		btnEncodeDecode.setBackground(Color.DARK_GRAY);
		btnEncodeDecode.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 128, 0), new Color(0, 128, 0), new Color(0, 128, 0), new Color(0, 128, 0)));
		btnEncodeDecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
//					ArrayList<String> errAry = new ArrayList<String>(2);

					int key = Integer.parseInt(tiKey.getText());
					
					encode( key );
					
				} catch( Exception e ) {
					//what to do?
					tiKey.requestFocus();
					tiKey.selectAll();
					// null 1st param will center in the desktop window
//					JOptionPane.showMessageDialog(null, "Please enter a number from -25 to 25 for the key" );
					// getContentPane() will center on the applet body
					JOptionPane.showMessageDialog(getContentPane(), "Please enter a number from -25 to 25 for the key" );
				}
			}
		});
		btnEncodeDecode.setBounds(359, 225, 222, 45);
		getContentPane().add(btnEncodeDecode);
		
		slKey = new JSlider(-26,26);
		slKey.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int key = slKey.getValue();
				tiKey.setText( "" + key );
				encode( key );
			}
		});
		slKey.setForeground(Color.WHITE);
		slKey.setBackground(Color.DARK_GRAY);
		slKey.setValue( Integer.parseInt(DEFAULT_KEY) );
		slKey.setSnapToTicks(true);
		slKey.setPaintTicks(true);
		slKey.setPaintLabels(true);
		slKey.setMinorTickSpacing(1);
		slKey.setMajorTickSpacing(13);
		slKey.setBounds(22, 175, 329, 60);
		getContentPane().add(slKey);
		
		JButton btnRecode = new JButton("^ Recode ^");
		btnRecode.setBackground(Color.DARK_GRAY);
		btnRecode.setForeground(Color.WHITE);
		btnRecode.setFont(new Font("Comfortaa", Font.PLAIN, 16));
		btnRecode.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 128, 0), new Color(0, 128, 0), new Color(0, 128, 0), new Color(0, 128, 0)));
		btnRecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String code = taOut.getText();
				int inverse = (26 - slKey.getValue()) % 26;
				taIn.setText(code);
				slKey.setValue(inverse);
			}
		});
		btnRecode.setBounds(12, 255, 112, 27);
		getContentPane().add(btnRecode);
	}
	

	private void encode( int key ) {
		
		String plaintext = taIn.getText();

//		if( plaintext.trim().length() == 0 ) {
//			JOptionPane.showMessageDialog(getContentPane(), "You haven't entered anything to be encoded!");
//			return;
//		}
		
		String ciphertext = caesar(plaintext, key);
		
		ciphertext = stringReverse(ciphertext);
		
		taOut.setText(ciphertext);
	}//encode/
	
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
