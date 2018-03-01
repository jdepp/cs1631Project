package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;
import java.lang.*;


import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VoteModel;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VoterTable;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VotingSoftware;

public class PollingActivity extends AppCompatActivity {
    VotingSoftware votingSoftware;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);

        SmsReceiver guy = new SmsReceiver();

        final TextView textViewToChange = (TextView) findViewById(R.id.pollingBeganText);
        textViewToChange.setText("Polling Began");
        votingSoftware = new VotingSoftware();
    }

    public void showVotes(View view) {
        VoterTable voterTable = votingSoftware.getVoterTable();

        final TextView votersTextView = (TextView) findViewById(R.id.voters);
        String currentVoters = "";

        for(VoteModel vm : voterTable.voterTable) {
            currentVoters = currentVoters + "\n" + vm.voteNumber;
            votersTextView.setText(currentVoters);
            Log.d("Curr vote", Integer.toString(vm.voteNumber));
        }
    }
}

