package com.example.secretmessages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

	InputMethodManager imm;

	EditText tiKey;
	EditText taInput;
	EditText taOutput;

	CheckBox cbRollKey;
	CheckBox cbReverse;
//	ToggleButton cbReverse;

	SeekBar slKey;
	Button btnEncode;
	Button btnRecode;
	Button btnDecode;

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		imm	= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		tiKey		= (EditText)	findViewById(R.id.tiKey);
		taInput		= (EditText)	findViewById(R.id.taInput);
		taOutput	= (EditText)	findViewById(R.id.taOutput);
		cbRollKey	= (CheckBox) 	findViewById(R.id.cbRollKey);
		cbReverse	= (CheckBox) 	findViewById(R.id.cbReverse);
//		cbReverse	= (ToggleButton) 	findViewById(R.id.cbReverse);
		slKey		= (SeekBar)		findViewById(R.id.slKey);
		btnEncode	= (Button)		findViewById(R.id.btnEncode);
		btnDecode	= (Button)		findViewById(R.id.btnDecode);
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
				imm.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY );
			}
		});

		btnDecode.setOnClickListener( new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				int key = Integer.parseInt(tiKey.getText().toString());
				decode( key );
				imm.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY );
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

		boolean rollKey = cbRollKey.isChecked();

//		String ciphertext = caesar(plaintext, key, rollKey );
		String ciphertext = vigenere(plaintext, key, rollKey );

		if( cbReverse.isChecked() ){
			ciphertext = stringReverse(ciphertext);
		}

		taOutput.setText(ciphertext);
	}//encode/


	private void decode( int key ){

		String plaintext = taInput.getText().toString();

		boolean rollKey = cbRollKey.isChecked();

		if( cbReverse.isChecked() ){
			plaintext = stringReverse(plaintext);
		}

//		String ciphertext = caesar(plaintext, key, rollKey );
		String ciphertext = vigenere(plaintext, key, rollKey );


		taOutput.setText(ciphertext);

	}//decode


	private static String vigenere( String str, int baseKey, boolean rollKey ) {
		int MAX = 127;// 126 is ~ (127 DEL)
		int MIN = 32;// 32 is space (31 unit separator)
		String csr = "";
		Log.d("vigenere/ Start:", str );

		//This is a test of 123 chars to 098 and !@#$%^&*()_+=-

		for( int i = 0; i < str.length(); i++ ){
			StringBuilder sb = new StringBuilder();
			int key = baseKey;
			int input = str.charAt(i);

			sb.append("Char: " +  input + " '" + (char) input + "'" );

			if( rollKey ){
				if( baseKey >= 0 ){
					key += i;
				} else {
					key -= i;
				}
			}

			if( baseKey >= 0 ){
				input += key;
				sb.append(" + key" + key + " => " +  input );//+ " '" + (char) input + "'" );
				input %= MAX;
				sb.append(" %" + MAX + " => "  +  input );//+ " '" + (char) input + "'" );
				if( input <= MIN ){
					input += MIN;
					sb.append(" +" + MIN + " => "  +  input );//+ " '" + (char) input + "'" );
				}

			} else {

				input += key;
				sb.append(" + key" + key + " => " +  input );//+ " '" + (char) input + "'" );

				if( input < MIN ){
					input -= MIN;
					sb.append(" -" + MIN + " => "  +  input );//+ " '" + (char) input + "'" );
					// then it was over to begin with
					input += MAX;
					sb.append(" +" + MAX + " => " +  input );//+ " '" + (char) input + "'" );
				}


				input %= MAX;
				sb.append(" %" + MAX + " => "  +  input );//+ " '" + (char) input + "'" );

			}


			sb.append(" => out: " + input + " (" + (char) + input + ")" );

			Log.d("", sb.toString() );

			csr += (char) input;

		}// for each char

		Log.d("vigenere/ End:", csr );
		return csr;

	}// vigenere




	private static String Xvigenere( String str, int baseKey, boolean rollKey ) {
//		char key = (char) baseKey;
		int MAX = 126;// 126 is ~
		int MIN = 31;// 32 is space
		int shift = baseKey >= 0 ? MIN : MIN;
		String csr = "";


		for( int i = 0; i < str.length(); i++ ) {

			StringBuilder sb = new StringBuilder();
			int input = str.charAt(i);
			int key = baseKey;

//			sb.append("Idx ");
//			sb.append( i );

			sb.append("Char: " +  input + " '" + (char) input + "'" );


			if( rollKey ){
				int j = i;//%26;

				if( baseKey > 0 || baseKey == 0 ){
					key += j;//keep incrementing the key
				}
				if( baseKey < 0 ){
					key -= j;//keep decrementing the key
				}
//				key = (char) baseKey;
//				key = baseKey;
			}

//			key += MAX;



			// Mod & shift have to be done in opposite order depending on key, else transformations
			// are not symmetric at the boundaries (32 & 127)

			if( baseKey > 0 || baseKey == 0 ){

				input += key;//shift the character
				sb.append(" + k" + key );

				input += MAX;
				sb.append(" + shift" + MAX );

				sb.append(" => " + input + " '" + (char) input + "'" );


				input %= MAX;//wrap it back to print chars

				sb.append(" %" + MAX + " => " + input + " (" + (char) input + ")" );

				if( input < MIN ){
					sb.append(" (< 32 shift +" + MIN + ") ");
					input += MIN;//shift up 32, 0-31 are non-printable
				}


			}
			else if( baseKey < 0 ){

				sb.append(" " + input + " +" + key );
				input += key;//shift the character
				sb.append(" => " + input + " '" + (char) input + "'" );

				int M = MIN;

				if( input % MIN < M ){
					sb.append(" (input was < " + M + " )");
					input -= M;
					input += MAX;
					sb.append(" so shifted down " + M + " and back up +" + MAX + " => " + input + " (" + (char) input + ")" );

//					input %= MAX;
//					sb.append(" moded: => " + input + " (" + (char) input + ")" );
				}

//				input %= MAX;//wrap it back to print chars

				sb.append(" %" + MAX + " => " + input + " (" + (char) input + " wrapped)" );

				input += key;//shift the character

				sb.append(" => " + input + " '" + (char) input + "'" );

			}


			sb.append(" => " );
			sb.append( input );
			sb.append( " (" + (char) input + ")" );

			Log.d("Final ", sb.toString() );

			csr += (char) input;

//			Log.d("Key", Character.toString(key) );


//			Log.d("Current Char:", Character.toString(input) );


//			if( input >= 'A' && input <= 'Z' ) {
//				input += key;
//
//				if( input > 'Z' ) {
//					input -= 26;//shift it back into the uppercase range
//				}
//				if( input < 'A' ) {
//					input += 26;
//				}
////				Log.d("A-Z Range", Character.toString(input) );
//
//			}
//			else if( input >= 'a' && input <= 'z' ) {
//
//				input += key;
//
//				if( input > 'z' ) {
//					input -= 26;//shift it back into the lowercase range
//				}
//				if( input < 'a' ) {
//					input += 26;
//				}
////				Log.d("a-z Range", Character.toString(input) );
//			}
//			else if( input >= '0' && input <= '9' ) {
//				input += (baseKey % 10);
//
//				if( input > '9' ) {
//					input -= 10;
//				}
//				else if( input < '0' ) {
//					input += 10;
//				}
////				Log.d("0-9 Range", Character.toString(input) );
//			}
//			input += key;
//			sb.append(" => " );
//			sb.append( input );
//
//			Log.d("Final ", sb.toString() );
//
//			csr += (char) input;
		}//for each char

		return csr;
	}//vigenere/


// This is a test of a long sentences with many more characters than expected & punctuation
// 1234567890 )(*&^%$#@!~`\][|}{';":/.,?><.

	private static String caesar( String str, int baseKey, boolean rollKey ) {
		char key = (char) baseKey;
		String csr = "";

		for( int i = 0; i < str.length(); i++ ) {

			StringBuilder sb = new StringBuilder();
			sb.append("Idx ");
			sb.append( i );

//			if( rollKey ){
//				int j = i;//%26;
//				sb.append(", Add ");
//				sb.append( j );
////				Log.d("Key Increment", Integer.toString(j) );
//				// Recast key to match types
//
//
//				if( baseKey > 0 ){
//					baseKey += j;//keep incrementing the key
//				}
//				if( baseKey < 0 ){
//					baseKey -= j;//keep decrementing the key
//				}
//				key = (char) baseKey;
//			}

			sb.append(", Key: ");
			sb.append( key );

//			Log.d("Key", Character.toString(key) );

			char input = str.charAt(i);
			sb.append(", Char:");
			sb.append( input );

//			Log.d("Current Char:", Character.toString(input) );


			if( input >= 'A' && input <= 'Z' ) {
				input += key;

				if( input > 'Z' ) {
					input -= 26;//shift it back into the uppercase range
				}
				if( input < 'A' ) {
					input += 26;
				}
//				Log.d("A-Z Range", Character.toString(input) );

			}
			else if( input >= 'a' && input <= 'z' ) {

				input += key;

				if( input > 'z' ) {
					input -= 26;//shift it back into the lowercase range
				}
				if( input < 'a' ) {
					input += 26;
				}
//				Log.d("a-z Range", Character.toString(input) );
			}
			else if( input >= '0' && input <= '9' ) {
				input += (baseKey % 10);

				if( input > '9' ) {
					input -= 10;
				}
				else if( input < '0' ) {
					input += 10;
				}
//				Log.d("0-9 Range", Character.toString(input) );
			}
			sb.append(" => " );
			sb.append(Character.toString(input));

			Log.d("Final ", sb.toString() );

			csr += input;
		}

		return csr;
	}//caesar/




	private static String stringReverse( String str ) {
		String rts = "";
		for( int l = str.length()-1; l >= 0; l-- ) {
			rts += str.charAt(l);
		}
		return rts;
	}//stringReverse/


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
