package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import android.telephony.SmsManager;
import android.widget.Toast;
import java.util.*;

import edu.pitt.cs.cs1635.jmd221.votingapp.PollingActivity;


/* Class that handles all of the logic for keeping track of voters and tallies */
public class VotingSoftware {
    private VoterTable voterTable;
    private TallyTable tallyTable;
    private PollingActivity pollingActivityContext;

    // Constructor that takes in an instance of the Polling Activity
    public VotingSoftware(PollingActivity context) {
        voterTable = new VoterTable();
        tallyTable = new TallyTable();
        pollingActivityContext = context;
    }

    // Checks if a voter's phone number is already in the voter table, if not then add them to voter table
    public void addVoteToVoterTable(String phone, String vote) {
        if (voterTable.isInVoterTable(phone)) {
            sendMessage(phone, vote, true, true);
        }
        else {
            int voteAsInt = Integer.parseInt(vote);
            voterTable.addVoter(phone, vote);
            sendMessage(phone, vote, false, true);
            addTallyToTallyTable(voteAsInt);
            Toast.makeText(pollingActivityContext, phone + ": vote received for " + vote, Toast.LENGTH_LONG).show();
        }
    }

    // Checks if the ID of the canidate being voted for is in the tally table, if so then increment their count by one,
    // if not then create a new tally with vote count of 1
    public void addTallyToTallyTable(int newId) {
        if(tallyTable.isInTallyTable(newId)) {
            int numVotes = tallyTable.getNumberOfVotes(newId);
            numVotes++;
            tallyTable.addTally(newId, numVotes);
        }
        else {
            tallyTable.addTally(newId, 1);
        }
    }

    // Sends a message back to the voter to let them know if their vote was successful or not
    public void sendMessage(String phone, String vote, boolean alreadyVoted, boolean validVote) {
        SmsManager smsManager = SmsManager.getDefault();
        if(validVote && !alreadyVoted) {
            String successMsg = "Successfully voted for \"" + vote + "\".";
            smsManager.sendTextMessage(phone, null, successMsg, null, null);
        }
        else if(validVote && alreadyVoted) {
            String alreadyVotedMsg = "Could not vote for \"" + vote + "\" - you already voted.";
            smsManager.sendTextMessage(phone, null, alreadyVotedMsg, null, null);
        }
        else {
            String invalidVoteMsg = "Could not vote for \"" + vote + "\" - not a valid vote.";
            smsManager.sendTextMessage(phone, null, invalidVoteMsg, null, null);
        }
    }

    // Returns the TallyTable (a HashMap)
    public Map<Integer, Integer> getTallyTable() {
        return tallyTable.getHashMap();
    }

    // Returns the VoterTable (a HashMap)
    public Map<String, String> getVoterTable() {
        return voterTable.getHashMap();
    }

}
