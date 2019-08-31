import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class BubblePanel extends JPanel {
	
	private int WIDTH;
	private int HEIGHT;
	
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	
	
	public BubblePanel( int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		bubbleList = new ArrayList<BubblePanel.Bubble>();
		setBackground(Color.BLACK);
//		testBubbles();
		addMouseListener( new BubbleListener() );//bind mouse click events to event delegate
		addMouseMotionListener( new BubbleListener() );//bind mouse drag / move events
		addMouseWheelListener( new BubbleListener() );//bind wheel movements
	}//BubblePanel/
	
	
	
	public void paintComponent( Graphics canvas ) {
		super.paintComponent(canvas);
		for( Bubble b: bubbleList ) {
			b.draw(canvas);
		}
	}//paintComponent/
	
	
	public void testBubbles() {
		for( int n = 0; n < 300; n++ ) {
			int x = rand.nextInt( WIDTH );
			int y = rand.nextInt( HEIGHT );
			int size = rand.nextInt(50);
			bubbleList.add( new Bubble( x, y, size) );
		}
		repaint();
	}//testBubbles/
	
	
	
	//create mouse event delegate class to encapsulate custom event driven behaviors
	private class BubbleListener extends MouseAdapter{
		
		public void mousePressed( MouseEvent e) {
			bubbleList.add( new Bubble(e.getX(), e.getY(), size) );
			repaint();
		}//mousePressed/
		
		public void mouseDragged( MouseEvent e ) {
			bubbleList.add( new Bubble(e.getX(), e.getY(), size) );
			repaint();
		}//mouseDragged/
		
		public void mouseWheelMoved( MouseWheelEvent e ) {
			
			int dir = System.getProperty("os.name").startsWith("Mac") ? -1 : 1;//normalize mac
			
			size += dir * e.getUnitsToScroll();
		}
		
	}//BubbleListener
	
	
	
	
	
	
	private class Bubble{
		private int x;
		private int y;
		private int size;
		private Color color;
		
		
		public Bubble( int newX, int newY, int newSize ){
			x = newX;
			y = newY;
			size = newSize;
			color = new Color(
				rand.nextInt(256)
				,rand.nextInt(256)
				,rand.nextInt(256)
			);
					
			
		}//Bubble()
		
		
		public void draw( Graphics canvas ) {
			canvas.setColor(color);
			canvas.fillOval(x-size/2, y-size/2, size, size);
		}
		
	}//Bubble

}///BubblePanel
