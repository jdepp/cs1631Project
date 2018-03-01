package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import android.widget.TextView;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.InputProcessor.SmsReceiver;
import edu.pitt.cs.cs1635.jmd221.votingapp.R;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class VotingSoftware {
    VoterTable voterTable;
    TallyTable tallyTable;
    SmsReceiver smsReceiver;

    public VotingSoftware() {
        voterTable = new VoterTable();
        tallyTable = new TallyTable();
        smsReceiver = new SmsReceiver();
    }

    public VoterTable getVoterTable() {
        return voterTable;
    }

}
