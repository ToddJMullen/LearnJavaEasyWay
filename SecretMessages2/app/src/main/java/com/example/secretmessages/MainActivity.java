package com.example.secretmessages;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

	EditText tiKey;
	EditText taInput;
	EditText taOutput;
	SeekBar slKey;
	Button btnEncode;
	Button btnRecode;

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		tiKey		= (EditText)	findViewById(R.id.tiKey);
		taInput		= (EditText)	findViewById(R.id.taInput);
		taOutput	= (EditText)	findViewById(R.id.taOutput);
		slKey		= (SeekBar)		findViewById(R.id.slKey);
		btnEncode	= (Button)		findViewById(R.id.btnEncode);
		btnRecode	= (Button)		findViewById(R.id.btnRecode);

		//handle incoming intent data
		Intent receivedIntent	= getIntent();
		String receivedText		= receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
		if( receivedText != null ){
			taInput.setText( receivedText );
		}

		btnEncode.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				int key = Integer.parseInt(tiKey.getText().toString());
//				taOutput.setText( tiKey.getText().toString() );// << for testing
				encode( key );
			}
		});
		btnRecode.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				String cipher = taOutput.getText().toString();
				taInput.setText( cipher );
				int enc = Integer.parseInt(tiKey.getText().toString());
				int dec = (-1*enc)  + 13;
				slKey.setProgress( dec );
				tiKey.setText( "" + (-1*enc) );
			}
		});
		slKey.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged( SeekBar seekBar, int i, boolean b ){
				int key = slKey.getProgress() - 13;
				tiKey.setText( "" + key );
				encode( key );
			}

			@Override
			public void onStartTrackingTouch( SeekBar seekBar ){

			}

			@Override
			public void onStopTrackingTouch( SeekBar seekBar ){

			}
		});


		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Secret Message, " + DateFormat.getDateTimeInstance().format(new Date()) );
				shareIntent.putExtra(Intent.EXTRA_TEXT, taOutput.getText().toString() );
				try {
					startActivity( Intent.createChooser(shareIntent, "Share Message..."));
					finish();
				} catch ( android.content.ActivityNotFoundException e ){
					Toast.makeText(MainActivity.this
						,"Error: Could Not Share!"
						,Toast.LENGTH_SHORT
					).show();
				}
			}
		});
	}//onCreate/


	private void encode( int key ) {

		String plaintext = taInput.getText().toString();

//		if( plaintext.trim().length() == 0 ) {
//			JOptionPane.showMessageDialog(getContentPane(), "You haven't entered anything to be encoded!");
//			return;
//		}

		String ciphertext = caesar(plaintext, key);

		ciphertext = stringReverse(ciphertext);

		taOutput.setText(ciphertext);
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



	@Override
	public boolean onCreateOptionsMenu( Menu menu ){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}//onCreateOptionsMenu/

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if( id == R.id.action_settings ){
			return true;
		}

		return super.onOptionsItemSelected(item);
	}//onOptionsItemSelected/




	}//MainActivity
