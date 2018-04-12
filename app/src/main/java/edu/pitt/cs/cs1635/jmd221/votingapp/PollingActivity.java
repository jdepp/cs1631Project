package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import android.view.*;
import java.lang.*;
import java.util.*;
import android.content.*;
import android.content.pm.*;


import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.Database;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.GetAllCandidatesListener;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.CandidateTable;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.TallyTable;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VotingSoftware;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver.Listener;


/* Activity that launches after the creation of candidates. This is where the voting takes place */
public class PollingActivity extends AppCompatActivity implements Listener, GetAllCandidatesListener {
    private VotingSoftware votingSoftware;
    public SmsReceiver receiver;
    public static boolean isInForeground;
    private String candidateSubject;

    // This method is called when this Activity is created - renders the view and creates VotingSoftware class and activates SmsListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        votingSoftware = new VotingSoftware(PollingActivity.this);

        // Get passed in CandidateTable from AddCandidatesActivity
        Intent intent = getIntent();
        votingSoftware.initializeCandidateTable((CandidateTable) intent.getSerializableExtra("CandidateTable"));
        displayTallies();

        // Deactivate send email button which should only be pressed when polling stopped
        Button sendEmailButton = (Button)findViewById(R.id.sendEmailButton);
        sendEmailButton.setEnabled(false);

        // Activate our SmsListener and listen for incoming SMS messages
        receiver = new SmsReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, mIntentFilter);
        receiver.setListener(new SmsReceiver.Listener() {
        @Override
            public void onTextReceived(String phone, String vote, boolean validMessage) {
                if(validMessage)
                    votingSoftware.addVoteToVoterTable(phone, vote);
                else
                    votingSoftware.sendMessage(phone, vote, false, false);

                displayTallies();
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

    // Stop listening for SMS messages and display results
    public void onStopPolling(View view) {
        isInForeground = false;
        unregisterReceiver(receiver);
        ComponentName receiver = new ComponentName(this, SmsReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        final TextView pollingStopped = (TextView) findViewById(R.id.pollingStartedText);
        pollingStopped.setText("Results");
        Button stopPollingButton = (Button)findViewById(R.id.stopPollingButton);
        stopPollingButton.setEnabled(false);
        Button sendEmailButton = (Button)findViewById(R.id.sendEmailButton);
        sendEmailButton.setEnabled(true);

        // Gets all Candidates from database and then compares each of the current year to the candidate table and if matches the ID, update its votes and save back to database
        final Database db = new Database();
        db.setGetYearsListener(new GetAllCandidatesListener() {
            @Override
            public void getCandidates(List<Candidate> candidates) {
                for(Candidate currentCandidate : candidates) {
                    for (Map.Entry<Integer, Integer> entry : votingSoftware.getTallyTable().entrySet()) {
                        if(Integer.toString(entry.getKey()).equals(currentCandidate.getId())) {
                            int updatedNumOfVotes = entry.getValue();
                            //currentCandidate.setNumVotes(Integer.toString(updatedNumOfVotes));
                            Candidate updatedCandidate = new Candidate(currentCandidate.getId(), currentCandidate.getName(), currentCandidate.getSubject(), currentCandidate.getYear());
                            updatedCandidate.setNumVotes(Integer.toString(updatedNumOfVotes));
                            db.createCandidate(updatedCandidate);
                            break;
                        }
                    }
                }
            }
        });

        //votingSoftware.destroyVoterTable();
    }

    // Sends the results of TallyTable in an email
    public void onSendEmail(View view) {
//        String emailBody = "";
//        for (Map.Entry<Integer, Integer> entry : votingSoftware.getTallyTable().entrySet()) {
//            emailBody = emailBody + "Candidate #" + entry.getKey() + " \"" + votingSoftware.getCandidateTable().get(Integer.toString(entry.getKey())) + "\" - " + entry.getValue().toString() + " votes\n";
//        }
//
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("message/rfc822");
//        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"schang@pitt.edu", "mackenzie@cs.pitt.edu"});
//        intent.putExtra(Intent.EXTRA_SUBJECT, "SCI Voting Results");
//        intent.putExtra(Intent.EXTRA_TEXT   , emailBody);
//        try {
//            startActivity(Intent.createChooser(intent, "Send results in email..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }

        // Transitions to ShowTrendsActivity
        Intent i = new Intent(getApplicationContext(), ShowTrendsActivity.class);
        startActivity(i);
    }

    // Displays the current tallies in the TallyTable in descending order
    private void displayTallies() {
        final TextView talliesTextView = (TextView) findViewById(R.id.tallies);
        String currentTallies = "";
        for (Map.Entry<Integer, Integer> entry : votingSoftware.getTallyTable().entrySet()) {
            currentTallies = currentTallies + "Candidate #" + entry.getKey() + " \"" + votingSoftware.getCandidateTable().get(Integer.toString(entry.getKey())) + "\" - " + entry.getValue().toString() + " votes\n";
        }
        talliesTextView.setText(currentTallies);
    }

    // Implements the onTextReceived() - this method is actually only called in the onCreate() method,
    // but Android Studio for some reason doesn't recognize that onTextReceived() is being implemented there,
    // so we created a dummy method here to shut it up
    public void onTextReceived(String s, String str, boolean validMessage) { }
    public void getCandidates(List<Candidate> candidates) { }

}

