package com.example.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

	private EditText tiGuess;

	private Button btnGuess;
	private Button btnPlayAgain;

	private TextView lblOutput;
	private TextView lblPrompt;
	private TextView lblAnswer;

	private RadioButton rbEasy;
	private RadioButton rbMedium;
	private RadioButton rbHard;
	private RadioButton rbCrazy;
	private RadioGroup rbLevels;

	private int theNumber;
	private int level = 1;
	private int max;
	private int attempts;


	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//bind the gui elements
		tiGuess = (EditText) findViewById(R.id.tiGuess);
		btnGuess = (Button) findViewById(R.id.btnGuess);
		btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
		lblOutput = (TextView) findViewById(R.id.lblOutput);
		lblAnswer = (TextView) findViewById(R.id.lblAnswer);
		lblPrompt = (TextView) findViewById(R.id.lblPrompt);
		rbEasy = (RadioButton) findViewById(R.id.rbEasy);
		rbMedium = (RadioButton) findViewById(R.id.rbMedium);
		rbHard = (RadioButton) findViewById(R.id.rbHard);
		rbCrazy = (RadioButton) findViewById(R.id.rbCrazy);
		rbLevels = (RadioGroup) findViewById(R.id.rbLevels);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		level = prefs.getInt("level", 1 );//pref name + fallback if DNE

		newGame();

		btnGuess.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				checkGuess();
			}
		});
		tiGuess.setOnEditorActionListener(new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction( TextView textView, int i, KeyEvent keyEvent ){
				checkGuess();
				return true;//return true mean don't close the keyboard
			}
		});
		btnPlayAgain.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				newGame();
			}
		});

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View view ){
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}//onCreate/


	public void newGame(){
//        boxLevels.setVisible(false);
//        btnPlayAgain.setVisible(false);
//        btnGuess.setVisible(true);
		tiGuess.setText("");
//		if( rbEasy.isChecked() ){
//			level = 1;
//		}
//		if( rbMedium.isChecked() ){
//			level = 2;
//		}
//		if( rbHard.isChecked() ){
//			level = 3;
//		}
//		if( rbCrazy.isChecked() ){
//			level = 5;
//		}
		attempts = 0;
		max = (int) Math.pow(10, level);
		lblOutput.setText("");
		lblPrompt.setText("Enter a number between 1 - " + max);
		tiGuess.setHint("1 - " + max);
		theNumber = (int) (Math.random() * max + 1);
		lblAnswer.setText("Cheat: " + theNumber);

		btnGuess.setVisibility(View.VISIBLE);
		btnPlayAgain.setVisibility(View.INVISIBLE);
		rbLevels.setVisibility(View.INVISIBLE);

	}//newGame/


	@Override
	public boolean onCreateOptionsMenu( Menu menu ){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}//onCreateOptionsMenu

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

		switch( id ){
			case R.id.action_settings:
				promptSettings();
				return true;
			case R.id.action_newgame:
				newGame();
				return true;
			case R.id.action_gamestats:
				return true;
			case R.id.action_about:
				openAbout();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}//onOptionsItemSelected/


	public void checkGuess(){
		String guessText = tiGuess.getText().toString();
		String msg = "";

		try{
			attempts++;
			int guess = Integer.parseInt(guessText);

			if( guess == theNumber ){
				msg = "Correct! The number is " + theNumber + "!"
//						+ " Keep going. Guess the new number!"
				;
				String tries = attempts == 1 ? " try!" : " tries!";
				lblPrompt.setText("You guessed right in " + attempts + tries);

				btnGuess.setVisibility(View.INVISIBLE);
				btnPlayAgain.setVisibility(View.VISIBLE);
//				rbLevels.setVisibility(View.VISIBLE);
			} else if( guess > theNumber ){
				msg = guess + " is too high. Try again.";
			} else {
				msg = guess + " is too low. Try again.";
			}


		} catch ( Exception e ){
			msg = "Enter a whole number between 1 and " + max;
		} finally {

			Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();//doesn't seem to do anything (in emulator?)

			lblOutput.setText(msg);
			tiGuess.requestFocus();
			tiGuess.selectAll();
		}


	}//checkGuess/


	public void promptSettings(){
//		final CharSequence[] items = {"1 to 10","1 to 100","1 to 1000"};
		final CharSequence[] items = {"Easy","Medium","Hard","Crazy"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Difficulty");
		builder.setItems(items, new DialogInterface.OnClickListener(){
			@Override
			public void onClick( DialogInterface dialogInterface, int i ){
				i++;
				Log.i("promptSettings/", "Level clicked:" + i );
				level = i;
				newGame();
				storeSettings();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}//promptSettings/


	public void openAbout(){
		final AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
		aboutDialog.setTitle("About Guessing Game");
		aboutDialog.setMessage("(c) 2019 Todd Mullen");
		aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
				, new DialogInterface.OnClickListener(){
					@Override
					public void onClick( DialogInterface dialogInterface, int i ){
						aboutDialog.dismiss();
					}
				});
		aboutDialog.show();
	}//openAbout


	public void storeSettings(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("level", level );
		editor.apply();
	}//storeSettings


}//MainActivity
