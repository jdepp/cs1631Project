package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartPolling(View view) {
        EditText edit = (EditText)findViewById(R.id.enterPasswordEditText);
        String content = edit.getText().toString();
        if(content.equals("password")) {
            Intent intent = new Intent(MainActivity.this, PollingActivity.class);
            startActivity(intent);
        }
        else {
            TextView tview = (TextView)findViewById(R.id.wrongPasswordText);
            tview.setText("Wrong password");
            tview.setTextColor(Color.RED);
        }
    }

}


