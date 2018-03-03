package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import java.lang.*;
import java.util.*;
import android.content.*;
import android.content.pm.*;


import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VotingSoftware;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver.Listener;


/* Activity that launches when the user types in the right password in Main Activity */
public class PollingActivity extends AppCompatActivity implements Listener {
    private VotingSoftware votingSoftware;
    public SmsReceiver receiver;
    public static boolean isInForeground;

    // This method is called when this Activity is created - renders the view and creates VotingSoftware class and activates SmsListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        final TextView pollingStarted = (TextView) findViewById(R.id.pollingBeganText);
        pollingStarted.setText("Polling Began");
        votingSoftware = new VotingSoftware(PollingActivity.this);


        // Activate our SmsListener
        receiver = new SmsReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, mIntentFilter);

        // Gets the phone and vote message from the SmsReceiver in onTextReceived(), adds voter to VoterTable,
        // and displays the current tallies
        receiver.setListener(new SmsReceiver.Listener() {
        @Override
            public void onTextReceived(String phone, String vote, boolean validMessage) {
                if(validMessage)
                    votingSoftware.addVoteToVoterTable(phone, vote);
                else
                    votingSoftware.sendMessage(phone, vote, false, false);

                final TextView talliesTextView = (TextView) findViewById(R.id.tallies);
                String currentTallies = "";
                for (Map.Entry<Integer, Integer> entry : votingSoftware.getTallyTable().entrySet()) {
                    currentTallies = currentTallies + "Project #" + entry.getKey() + " - " + entry.getValue().toString() + " votes\n";
                }
                talliesTextView.setText(currentTallies);
            }
        });
    }

    // Let SmsListener know if Polling Activity is in the foreground - if it is, listen for SMS messages
    @Override
    protected void onResume() {
        super.onResume();
        isInForeground = true;
    }

    // Let SmsListener know if Polling Activity is not in the foreground - don't listen for SmsMessages
    @Override
    protected void onPause() {
        super.onPause();
        isInForeground = false;
    }

    // Stop listening for SMS messages
    public void onStopPolling(View view) {
        unregisterReceiver(receiver);
        ComponentName receiver = new ComponentName(this, SmsReceiver.class);

        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        final TextView pollingStopped = (TextView) findViewById(R.id.pollingBeganText);
        pollingStopped.setText("Polling Stopped");
    }

    // Implements the onTextReceived() - this method is actually only called in the onCreate() method,
    // but Android Studio for some reason doesn't recognize that onTextReceived() is being implemented there,
    // so we created a dummy method here to shut it up
    public void onTextReceived(String s, String str, boolean validMessage) { }

}

