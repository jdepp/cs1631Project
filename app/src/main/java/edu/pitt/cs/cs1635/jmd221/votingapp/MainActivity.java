package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

/* Main Activity that gets called when the app first boots up */
public class MainActivity extends AppCompatActivity {

    // Method called on creation of MainActivity - renders the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method called when "Start Polling" button pressed
    public void onStartPolling(View view) {
        EditText edit = (EditText)findViewById(R.id.enterPasswordEditText);
        String content = edit.getText().toString();

        // Start Polling Activity is the inputted text equals the password
        if(content.equals("password")) {
            Intent intent = new Intent(MainActivity.this, PollingActivity.class);
            startActivity(intent);
        }
        // Wrong password - display "Wrong Password" and don't start Polling Activity
        else {
            TextView textView = (TextView)findViewById(R.id.wrongPasswordText);
            textView.setText("Wrong password");
            textView.setTextColor(Color.RED);
        }
    }

}


