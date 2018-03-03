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

public class PollingActivity extends AppCompatActivity implements Listener {
    public SmsReceiver receiver;
    private VotingSoftware votingSoftware;
    public static boolean isInForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        receiver = new SmsReceiver();
        //registerReceiver(receiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
        //receiver.setOnSmsReceivedListener(this);

        final TextView pollingStarted = (TextView) findViewById(R.id.pollingBeganText);
        pollingStarted.setText("Polling Began");
        votingSoftware = new VotingSoftware();

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, mIntentFilter);

        receiver.setListener(new SmsReceiver.Listener() {
        @Override
            public void onTextReceived(String phone, String vote) {
                votingSoftware.addVoteToVoterTable(phone, vote);

                final TextView talliesTextView = (TextView) findViewById(R.id.tallies);
                String currentTallies = "";
                for (Map.Entry<Integer, Integer> entry : votingSoftware.tallyTable.tallyTable.entrySet()) {
                    currentTallies = currentTallies + "Project #" + entry.getKey() + " has " + entry.getValue().toString() + "votes\n";
                }
                talliesTextView.setText(currentTallies);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInForeground = false;
    }

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

    public void onTextReceived(String phone, String vote) {
        votingSoftware.addVoteToVoterTable(phone, vote);

        final TextView talliesTextView = (TextView) findViewById(R.id.tallies);
        String currentTallies = "";
        for (Map.Entry<Integer, Integer> entry : votingSoftware.tallyTable.tallyTable.entrySet()) {
            currentTallies = currentTallies + "Project" + entry.getKey() + " - " + entry.getValue().toString() + " votes\n";
        }
        talliesTextView.setText(currentTallies);
    }


}

