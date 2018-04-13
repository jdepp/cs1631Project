package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

/* Main Activity that gets called when the app first boots up */
public class MainActivity extends AppCompatActivity {

    // Method called on creation of MainActivity - renders the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText passwordInput = findViewById(R.id.enterPasswordEditText);
        final Button passwordButton = findViewById(R.id.enterPasswordButton);
        passwordButton.setEnabled(false);

        // Listen for text input changes and make "Login" button disabled if password text field is blank
        passwordInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0)
                    passwordButton.setEnabled(false);
                else
                    passwordButton.setEnabled(true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
    }

    // Method called when "Start Polling" button pressed
    public void onGetCandidates(View view) {
        EditText edit = findViewById(R.id.enterPasswordEditText);
        String content = edit.getText().toString();

        // Start Polling Activity is the inputted text equals the password
        if(content.equals("password")) {
            Intent intent = new Intent(MainActivity.this, AddCandidatesActivity.class);
            startActivity(intent);
        }
        // Wrong password - display "Wrong Password" and don't start Polling Activity
        else {
            TextView textView = findViewById(R.id.wrongPasswordText);
            textView.setText("Wrong password");
            textView.setTextColor(Color.RED);
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            edit.setText("");
        }
    }

}


