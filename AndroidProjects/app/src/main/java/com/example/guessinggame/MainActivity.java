package com.example.guessinggame;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText tiGuess;
    private Button btnGuess;
    private TextView lblOutput;

    private int theNumber;
    private int level = 1;
    private int max;



    public void newGame() {
//        boxLevels.setVisible(false);
//        btnPlayAgain.setVisible(false);
//        btnGuess.setVisible(true);
        tiGuess.setText("");
//        if( rdbtnEasy.isSelected() ) {
//            level = 1;
//        }
//        if( rdbtnMedium.isSelected() ) {
//            level = 2;
//        }
//        if( rdbtnHard.isSelected() ) {
//            level = 3;
//        }
//        if( rdbtnCrazy.isSelected() ) {
//            level = 5;
//        }
//        attempts = 0;
        max = (int)Math.pow(10, level);
        theNumber = (int)(Math.random() * max  + 1);
//        lblPrompt.setText("Guess a number between 1 & " + max);
//        boxPrompt.setVisible(true);
    }//newGame/


    public void checkGuess() {
        String guessText = tiGuess.getText().toString();
        String msg = "";

        try {
//            attempts++;
            int guess = Integer.parseInt(guessText);

            if( guess == theNumber ) {
                msg = "Correct! The number is " + theNumber + "!"
//						+ " Keep going. Guess the new number!"
                ;
//                String tries = attempts == 1 ? " try!" : " tries!";
//                lblNumTries.setText("You guessed right in " + attempts + tries);
//                btnGuess.setVisible(false);//

//                lblNumTries.setVisible(true);
//                boxPrompt.setVisible(false);
//                boxLevels.setVisible(true);
//                btnPlayAgain.setVisible(true);
//				newGame();
            }
            else if( guess > theNumber ) {
                msg = guess + " is too high. Try again.";
            }
            else {
                msg = guess + " is too low. Try again.";
            }


        } catch( Exception e ) {
            msg = "Enter a whole number between 1 and " + max;
        } finally {

            lblOutput.setText(msg);
            tiGuess.requestFocus();
            tiGuess.selectAll();
        }


    }//checkGuess/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind the gui elements
        tiGuess     = (EditText) findViewById(R.id.tiGuess);
        btnGuess    = (Button) findViewById(R.id.btnGuess);
        lblOutput   = (TextView) findViewById(R.id.lblOutput);

        newGame();

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });
        tiGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                checkGuess();
                return true;//return true mean don't close the keyboard
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
