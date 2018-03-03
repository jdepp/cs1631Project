package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class VoterTable {
    public Map<String, String> voterTable;

    public VoterTable() {
        voterTable = new HashMap<>();
    }

    public void addVoter(String phoneNumber, String vote) {
        voterTable.put(phoneNumber, vote);
    }

}
