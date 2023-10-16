package com.mj_bonifacio.numberguesser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView indicatorTxt, attemptsTxt, correctInpTxt;
    Button checkBtn, resetBtn;
    EditText numberInp;

    // Attempts number
    int attemptsNum = 0;
    int randomizer;
    int luck; // Responsible for 25% chance of winning

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextViews
        indicatorTxt = (TextView) findViewById(R.id.txtIndicator);
        attemptsTxt = (TextView) findViewById(R.id.txtAttempts);
        correctInpTxt = (TextView) findViewById(R.id.txtCorrectInput);

        // Buttons
        checkBtn = (Button) findViewById(R.id.btnCheck);
        resetBtn = (Button) findViewById(R.id.btnQuit);

        // EditText
        numberInp = (EditText) findViewById(R.id.inpNumber);

        Random rd = new Random();
        randomizer = rd.nextInt(100) + 1;
        luck = rd.nextInt(5);

        resetBtn.setEnabled(false);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int convertStr = Integer.parseInt(String.valueOf(numberInp.getText()));
                String inputStr = numberInp.getText().toString();

                if (inputStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Kindly enter a number first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Remove the comment in the code in order to check if the luck is actually 1
                correctInpTxt.setText("LOG: " + Integer.toString(randomizer));

                boolean winRate = winProb();

                if (winRate){
                    // correctInpTxt.setText("Correct Input: " + Integer.toString(convertStr));
                    indicatorTxt.setText("CORRECT!");
                    checkBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                }
                else {
                    hint(convertStr);
                }

                // If attempt number reached 100.
                if (attemptsNum >= 100){
                    Toast.makeText(MainActivity.this, "Kindly enter a number first.", Toast.LENGTH_SHORT).show();
                    checkBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                    return;
                }

                attemptsNum++;
                attemptsTxt.setText("Attempts: " + Integer.toString(attemptsNum));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorTxt.setText("Indicator");
                attemptsTxt.setText("Attempts: 0");
                correctInpTxt.setText("Correct Input: 0");
                numberInp.setText("");
                randomizer = new Random().nextInt(100) + 1;
                attemptsNum = 0;
                checkBtn.setEnabled(true);
                resetBtn.setEnabled(false);
            }
        });
    }

    // 25% Winning
    private boolean winProb() {
        int winThreshold = 4;
        if (luck == winThreshold) {
            return true; // If luck is equal to 1, then there's 25% chance.
        }
        else{
            return false;
        }
    }

    // Hints
    private void hint(int guess){
        if (guess > randomizer){
            indicatorTxt.setText("LOWER");
        }
        if (guess < randomizer){
            indicatorTxt.setText("HIGHER");
        }
    }
}