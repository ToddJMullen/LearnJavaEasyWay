package com.example.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
//import android.widget.ImageView;//replace because AS warned to use the AppCompatImageView which adds 'magic' enhancements
import android.view.View;
import android.os.Handler;//had to add manually, was only suggesting java.util.logging.Handler
import android.widget.Button;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.Random;

public class BubbleView
//		extends ImageView
		extends AppCompatImageView
		implements View.OnTouchListener{

	//imported from Swing version
	private final int MIN_BUBBLE_SIZE = 3;
	private final int MAX_BUBBLE_SIZE = 300;
	private final int MAX_SPEED = 15;

	private final String LABEL_PAUSE = "Pause";
	private final String LABEL_START = "Start";
	private final String LABEL_SOFT_BOUNCE = "Soft Bounce";
	private final String LABEL_HARD_BOUNCE = "Hard Bounce";

	boolean paused		= false;
	boolean random		= true;
	boolean bounce		= true;
	boolean bounceHard	= true;
	boolean drawPixels	= false;
	//end imported from Swing version


	private int WIDTH;
	private int HEIGHT;
	private int DEFAULT_SPEED = 15;
	private int SPEED_DIFF	= 1;


	private Random rand	= new Random();
	private int size	= 50;
	private int delay	= 33;
	private ArrayList<Bubble> bubbleList;

//	private Button	btnClear;

	private Paint myPaint	= new Paint();
	private Handler h		= new Handler();




	public BubbleView( Context context, AttributeSet attributeSet ){
		super( context, attributeSet );
		WIDTH = 1000;//getWidth();
		HEIGHT = 1500;//getHeight();
		bubbleList = new ArrayList<Bubble>();
		blowBubbles();
		setOnTouchListener( this );
//		btnClear = (Button) findViewById(R.id.btnClear);
		//app will not start/run is either of these listeners is bound
//		btnClear/.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch( View view, MotionEvent motionEvent ){
//
//				return false;
//			}
//		});
//		btnClear.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick( View view ){
//				bubbleList = new ArrayList<Bubble>();
//				invalidate();
//			}
//		});
	}//BubbleView


	private Runnable r = new Runnable(){
		@Override
		public void run(){
		for( Bubble b: bubbleList ){
			b.update();
		}
		invalidate();
		}
	};


	protected void onDraw( Canvas canvas ){
		WIDTH = getWidth();
		HEIGHT = getHeight();
		for( Bubble b: bubbleList ){
			b.draw( canvas );
		}
		h.postDelayed(r, delay );
	}//onDraw/



	public void blowBubbles(){
		for( int n = 0; n < 100; n++ ){
			int x = rand.nextInt(WIDTH);
			int y = rand.nextInt(HEIGHT);
			int s = rand.nextInt(size) + size;
			bubbleList.add( new Bubble(x,y,s) );
		}
		invalidate();
	}//blowBubbles/


	@Override
	public boolean onTouch( View view, MotionEvent motionEvent ){
		int pointers = motionEvent.getPointerCount();
		for( int n = 0; n < pointers; n++ ){
			int x = (int) motionEvent.getX(n);
			int y = (int) motionEvent.getY(n);
			int s = rand.nextInt( size ) + size;
			bubbleList.add( new Bubble(x, y, s) );
		}
		return true;//return false makes no change when trying to bind listeners to btnClear
	}





	///////////////////////////////////////////////////
	///////////////////////////////////////////////////
	//// Bubble Class Definition
	///////////////////////////////////////////////////


	private class Bubble{
		private int x;
		private int y;
		private int dx, dy;
		private int size;
		private int radius;
		private int color;


		public Bubble( int newX, int newY, int newSize ){
			if( drawPixels ) {
				x = (newX / newSize) * newSize + newSize/2;
				y = (newY / newSize) * newSize + newSize/2;
			}
			else {
				x = newX;
				y = newY;
			}
			dx = 0;
			dy = 0;

			size = newSize;
			radius = size/2;
			color = Color.argb(//argb => alpha, red, green, blue
					rand.nextInt(256)//alpha
					,rand.nextInt(256)//red
					,rand.nextInt(256)//green
					,rand.nextInt(256)//blue
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


		public void draw( Canvas canvas ) {
			myPaint.setColor( color );
			canvas.drawOval(x-size/2, y-size/2, x + size/2, y + size/2, myPaint );
			//Swing version
//			canvas.setColor(color);
//			if( drawPixels ) {
//				canvas.fillRect(x-size/2, y-size/2, size, size);
//			}
//			else {
//				canvas.fillOval(x-size/2, y-size/2, size, size);
//			}
		}//draw/


		public void update() {
			x += dx;
			y += dy;
			if( bounce ) {
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
			}
			else {

				//loop bubbles vertically
				if( y <= 0 ) {
					y = HEIGHT;
				}
				else if( y >= HEIGHT ) {
					y = 0;
				}
				//loop bubbles horizontally
				if( x <= 0 ) {
					x = WIDTH;
				}
				else if( x >= WIDTH ) {
					x = 0;
				}
			}
		}//update/

	}//Bubble


}//BubbleView
