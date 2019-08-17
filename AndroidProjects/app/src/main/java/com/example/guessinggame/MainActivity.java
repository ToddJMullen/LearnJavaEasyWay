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

	private static int MAX_TRIES = 3;

	private static String NAME_NUM_GAMES	= "numGames";
	private static String NAME_LOSSES		= "numLosses";
	private static String NAME_LEVEL		= "level";
	private static String NAME_WINS			= "numWins";

	SharedPreferences			prefs;
	SharedPreferences.Editor	history;

	private EditText tiGuess;

	private Button btnGuess;
	private Button btnPlayAgain;

	private TextView lblOutput;
	private TextView lblPrompt;
	private TextView lblAnswer;
	private TextView lblNumGames;
	private TextView lblNumLosses;
	private TextView lblNumWins;

	private RadioButton rbEasy;
	private RadioButton rbMedium;
	private RadioButton rbHard;
	private RadioButton rbCrazy;
	private RadioGroup rbLevels;

	private int theNumber;
	private int level = 1;
	private int max;
	private int tries;
	private int numGames;
	private int numWins;
	private int numLosses;


	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//bind the gui elements
		tiGuess 		= (EditText) findViewById(R.id.tiGuess);
		btnGuess		= (Button) findViewById(R.id.btnGuess);
		btnPlayAgain	= (Button) findViewById(R.id.btnPlayAgain);
		lblOutput 		= (TextView) findViewById(R.id.lblOutput);
		lblAnswer 		= (TextView) findViewById(R.id.lblAnswer);
		lblPrompt 		= (TextView) findViewById(R.id.lblPrompt);
		lblNumGames		= (TextView) findViewById(R.id.lblNumGames);
		lblNumWins		= (TextView) findViewById(R.id.lblNumWins);
		lblNumLosses	= (TextView) findViewById(R.id.lblNumLosses);
//		rbEasy 			= (RadioButton) findViewById(R.id.rbEasy);
//		rbMedium 		= (RadioButton) findViewById(R.id.rbMedium);
//		rbHard 			= (RadioButton) findViewById(R.id.rbHard);
//		rbCrazy 		= (RadioButton) findViewById(R.id.rbCrazy);
//		rbLevels 		= (RadioGroup) findViewById(R.id.rbLevels);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		history = prefs.edit();

		level	= prefs.getInt(NAME_LEVEL, 1 );//pref name , 1 => fallback if DNE

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

		numGames	= prefs.getInt( NAME_NUM_GAMES, 0 ) + 1;
		numWins		= prefs.getInt(NAME_WINS, 0 );
		numLosses	= prefs.getInt(NAME_LOSSES, 0 );

		history.putInt( NAME_NUM_GAMES, numGames );
		history.apply();

		writeStats();


		tiGuess.setText("");
		tries = 0;
		max = (int) Math.pow(10, level);
		lblOutput.setText("");
		lblPrompt.setText("Enter a number between 1 - " + max);
		tiGuess.setHint("1 - " + max);
		theNumber = (int) (Math.random() * max + 1);
		lblAnswer.setText("Cheat: " + theNumber);

		btnGuess.setVisibility(View.VISIBLE);
		btnPlayAgain.setVisibility(View.INVISIBLE);
//		rbLevels.setVisibility(View.INVISIBLE);

	}//newGame/


	public void writeStats(){
		lblNumGames		.setText( numGames + " Games");
		lblNumWins		.setText( numWins + " Wins");
		lblNumLosses	.setText( numLosses + " Losses");
	}


	public void checkGuess(){
		String guessText = tiGuess.getText().toString();
		String msg = "";

		try{
			tries++;
			int guess = Integer.parseInt(guessText);

			if( tries <= MAX_TRIES ){
				if( guess == theNumber ){
					msg = "Correct! The number is " + theNumber + "!"
//						+ " Keep going. Guess the new number!"
					;
					String noun = tries == 1 ? " try!" : " tries!";
					lblPrompt.setText("You guessed right in " + tries + noun);

					btnGuess.setVisibility(View.INVISIBLE);
					btnPlayAgain.setVisibility(View.VISIBLE);
//				rbLevels.setVisibility(View.VISIBLE);
//					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

					numWins = prefs.getInt(NAME_WINS, 0 ) + 1;
					history.putInt(NAME_WINS, numWins );
					history.apply();
//					SharedPreferences.Editor editor = prefs.edit();
//					editor.putInt("numWins", numWins );
//					editor.apply();

				} else if( guess > theNumber ){
					msg = guess + " is too high. Try again.";
				} else {
					msg = guess + " is too low. Try again.";
				}
			}
			else {
				msg = "You lose dirtbag!";
				lblPrompt.setText(msg);
//				lblPrompt.setTextSize(25);
				btnGuess.setVisibility(View.INVISIBLE);
				btnPlayAgain.setVisibility(View.VISIBLE);

				numLosses = 1 + prefs.getInt(NAME_LOSSES, 0 );
				history.putInt(NAME_LOSSES, numLosses );
				history.apply();
			}


		} catch ( Exception e ){
			msg = "Enter a whole number between 1 and " + max;

		} finally {

			Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();//doesn't seem to do anything (in emulator?)

			lblOutput.setText(msg);
//			lblPrompt.setTextSize(11);
			tiGuess.requestFocus();
			tiGuess.selectAll();
			writeStats();
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
				dialogInterface.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}//promptSettings/



	public void openStats(){
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		int numWins = prefs.getInt( NAME_WINS, 0 );
		AlertDialog dlgStats = new AlertDialog.Builder(MainActivity.this).create();
		dlgStats.setTitle("Guessing Game Stats");
		float pctWin = (float) 100 * numWins/numGames;
		float pctLose = (float) 100 * numLosses/numGames;
		String msg = "Nice Job!";
		if( pctWin < pctLose ){
			msg = "You Suck!";
		}
		dlgStats.setMessage(
			"You have played a total of " + numGames + " games (all time)."
			+ numWins + " Wins " + pctWin + "% & " + numLosses + " Losses " + pctLose + "%"
			+ " " + msg
		);
		dlgStats.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
				, new DialogInterface.OnClickListener(){
					@Override
					public void onClick( DialogInterface dialogInterface, int i ){
						dialogInterface.dismiss();
					}
		});
		dlgStats.show();
	}//openStats/



	public void openAbout(){
		final AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
		aboutDialog.setTitle("About Guessing Game");
		aboutDialog.setMessage("(c) 2019 Todd Mullen");
		aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
				, new DialogInterface.OnClickListener(){
					@Override
					public void onClick( DialogInterface dialogInterface, int i ){
						dialogInterface.dismiss();
					}
				});
		aboutDialog.show();
	}//openAbout



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
				openStats();
				return true;
			case R.id.action_about:
				openAbout();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}//onOptionsItemSelected/




	public void storeSettings(){
		history.putInt(NAME_LEVEL, level);
		history.apply();
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putInt("level", level );
//		editor.apply();
	}//storeSettings


}//MainActivity
