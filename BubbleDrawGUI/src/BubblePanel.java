import java.awt.event.*;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

public class BubblePanel extends JPanel {

	private final int MIN_BUBBLE_SIZE = 3;
	private final int MAX_BUBBLE_SIZE = 300;
	private final int MAX_SPEED = 5;

	private final String LABEL_PAUSE = "Pause";
	private final String LABEL_START = "Start";
	private final String LABEL_SOFT_BOUNCE = "Soft Bounce";
	private final String LABEL_HARD_BOUNCE = "Hard Bounce";
	
	private int WIDTH;
	private int HEIGHT;
	private int DEFAULT_SPEED = 5;
	private int SPEED_DIFF	= 1;
	
	
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	Timer timer;
	JToggleButton btnToggleBounce;
	JSlider slSpeed;
	JCheckBox cbRandom;
	JLabel lblDyDx;
	
	int delay	= 33;
	int size	= 25;
	boolean paused	= false;
	boolean random	= true;
	boolean bounceHard	= true;
	
	
	public BubblePanel( int width, int height) {
		WIDTH		= width;
		HEIGHT		= height;
		timer		= new Timer(delay, new BubbleListener() );
		bubbleList	= new ArrayList<BubblePanel.Bubble>();
		
		setBackground(Color.DARK_GRAY);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblNewLabel = new JLabel("Speed");
		panel.add(lblNewLabel);
		
		slSpeed = new JSlider();
		slSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int speed = slSpeed.getValue() + 1;
				int delay = 1000 / speed;
				timer.setDelay(delay);
			}
		});
		slSpeed.setPaintLabels(true);
		slSpeed.setPaintTicks(true);
		slSpeed.setMinorTickSpacing(5);
		slSpeed.setMajorTickSpacing(30);
		slSpeed.setMaximum(120);
		panel.add(slSpeed);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				paused = !paused;
				if( paused ) {
					timer.stop();
					btn.setText(LABEL_START);
				}
				else {
					timer.start();
					btn.setText(LABEL_PAUSE);
				}
			}
		});
		panel.add(btnPause);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<BubblePanel.Bubble>();
				repaint();
			}
		});
		panel.add(btnClear);
		
		cbRandom = new JCheckBox("Random?");
		cbRandom.setSelected(true);
		cbRandom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				random = cbRandom.isSelected();
				if( !random ) {
					updateDyDx();
				}
			}
		});
		panel.add(cbRandom);
		
		JButton btnUpdateRandom = new JButton("Change dy/dx");
		btnUpdateRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDyDx();
			}
		});
		panel.add(btnUpdateRandom);
		
		lblDyDx = new JLabel("dy: 5, dx: 3");
		panel.add(lblDyDx);
		
		btnToggleBounce = new JToggleButton( LABEL_HARD_BOUNCE );
		btnToggleBounce.setSelected(bounceHard);
		btnToggleBounce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bounceHard = btnToggleBounce.isSelected();
				if( bounceHard ) {
					btnToggleBounce.setText( LABEL_HARD_BOUNCE );
				}
				else {
					btnToggleBounce.setText( LABEL_SOFT_BOUNCE );
				}
			}
		});
		panel.add(btnToggleBounce);
		
		testBubbles();
		addMouseListener( new BubbleListener() );//bind mouse click events to event delegate
		addMouseMotionListener( new BubbleListener() );//bind mouse drag / move events
		addMouseWheelListener( new BubbleListener() );//bind wheel movements
		timer.start();
	}//BubblePanel/
	
	private void updateDyDx(){
		int lastSpeed	= DEFAULT_SPEED;
		int lastDiff	= SPEED_DIFF;
		DEFAULT_SPEED	= 0;	
		SPEED_DIFF		= 0;
		do {
			DEFAULT_SPEED = rand.nextInt( MAX_SPEED * 2 + 1) - MAX_SPEED;
		} while ( DEFAULT_SPEED < 1 || DEFAULT_SPEED == lastSpeed );
		do {
			SPEED_DIFF = rand.nextInt( DEFAULT_SPEED * 2 + 1);
		} while( SPEED_DIFF < 1 || SPEED_DIFF == lastDiff );
		lblDyDx.setText( "dx: " + DEFAULT_SPEED + ", dy: " + SPEED_DIFF );
	}
	
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
			int size = rand.nextInt( MAX_BUBBLE_SIZE );
			bubbleList.add( new Bubble( x, y, size) );
		}
		repaint();
	}//testBubbles/
	
	
	
	//create mouse event delegate class to encapsulate custom event driven behaviors
	private class BubbleListener
		extends MouseAdapter
		implements ActionListener{
		
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
			if( size < MIN_BUBBLE_SIZE ) {
				size = MIN_BUBBLE_SIZE;
			}
			else if( size > MAX_BUBBLE_SIZE ) {
				size = MAX_BUBBLE_SIZE;
			}
		}//mouseWheelMoved/
		
		
		
		public void actionPerformed( ActionEvent e) {
			for( Bubble b : bubbleList ) {
				b.update();
			}
			repaint();
			
		}//actionPerformed/
		
	}//BubbleListener
	
	
	
	
	
	
	private class Bubble{
		private int x;
		private int y;
		private int dx, dy;
		private int size;
		private int radius;
		private Color color;
		
		
		public Bubble( int newX, int newY, int newSize ){
			x = newX;
			y = newY;
			dx = 0;
			dy = 0;
			
			size = newSize;
			radius = size/2;
			color = new Color(
				rand.nextInt(256)//red
				,rand.nextInt(256)//green
				,rand.nextInt(256)//blue
				,rand.nextInt(256)//alpha
			);
			
			
			if( random ) {
				do {
					dx = rand.nextInt( MAX_SPEED * 2 + 1) - MAX_SPEED;				
				} while ( dx == 0 );
				
				do {
					dy = rand.nextInt( MAX_SPEED * 2 + 1) - MAX_SPEED;
				} while( dy == 0 );
			}
			else {
				dx = DEFAULT_SPEED;
				dy = DEFAULT_SPEED + SPEED_DIFF;
			}
			
					
			
		}//Bubble()
		
		
		public void draw( Graphics canvas ) {
			canvas.setColor(color);
			canvas.fillOval(x-size/2, y-size/2, size, size);
		}
		
		
		public void update() {
			x += dx;
			y += dy;
			if( bounceHard ) {
				
				//hard bounce (perimeters)
				if( x - radius <= 0 || x + radius >= WIDTH ) {
					dx *= -1;
				}
				if( y - radius <= 0 || y + radius >= HEIGHT ) {
					dy *= -1;
				}
			}
			else {				
				//soft bounce (centers)
				if( x <= 0 || x >= WIDTH ) {
					dx *= -1;
				}
				if( y <= 0 || y >= HEIGHT ) {
					dy *= -1;
				}
			}

//			//loop bubbles vertically
//			if( y <= 0 ) {
//				y = HEIGHT;
//			}
//			else if( y >= HEIGHT ) {
//				y = 0;
//			}
//			//loop bubbles horizontally
//			if( x <= 0 ) {
//				x = WIDTH;
//			}
//			else if( x >= WIDTH ) {
//				x = 0;
//			}
		}
		
	}//Bubble

}///BubblePanel
