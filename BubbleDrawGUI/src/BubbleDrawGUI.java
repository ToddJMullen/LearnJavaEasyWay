import javax.swing.JFrame;

public class BubbleDrawGUI extends JFrame {
	
	public static final int WIDTH	= 900;
	public static final int HEIGHT	= 800;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Todd's BubbleDraw Bounce GUI App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BubblePanel(WIDTH,HEIGHT));
		frame.setSize(new java.awt.Dimension(WIDTH,HEIGHT));
		frame.setVisible(true);
	}//main

}//BubbleDraw
