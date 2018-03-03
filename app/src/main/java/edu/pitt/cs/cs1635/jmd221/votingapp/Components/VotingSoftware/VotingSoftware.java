package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver;
import edu.pitt.cs.cs1635.jmd221.votingapp.R;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class VotingSoftware {
    public VoterTable voterTable;
    public TallyTable tallyTable;

    public VotingSoftware() {
        voterTable = new VoterTable();
        tallyTable = new TallyTable();
    }

    public void addVoteToVoterTable(String phone, String vote) {
        if (voterTable.voterTable.containsKey(phone)) {
            sendMessage(phone, vote, false);
        }
        else {
            int voteAsInt = Integer.parseInt(vote);
            voterTable.addVoter(phone, vote);
            sendMessage(phone, vote, true);
            addTallyToTallyTable(voteAsInt);
        }
    }

    public void addTallyToTallyTable(int newId) {
        if(tallyTable.tallyTable.containsKey(newId)) {
            int numVotes = tallyTable.tallyTable.get(newId);
            numVotes++;
            tallyTable.tallyTable.put(newId, numVotes);
        }
        else {
            tallyTable.tallyTable.put(newId, 1);
        }
    }

    private void sendMessage(String phone, String vote, boolean validVote) {
        SmsManager smsManager = SmsManager.getDefault();
        if(validVote) {
            String voteAsk = "Successfully voted for " + vote + ".";
            smsManager.sendTextMessage(phone, null, voteAsk, null, null);
        }
        else {
            String voteAsk = "Could not vote for " + vote + " - you already voted.";
            smsManager.sendTextMessage(phone, null, voteAsk, null, null);
        }
    }

    public VoterTable getVoterTable() {
        return voterTable;
    }

    public TallyTable getTallyTable() {
        return tallyTable;
    }

}
