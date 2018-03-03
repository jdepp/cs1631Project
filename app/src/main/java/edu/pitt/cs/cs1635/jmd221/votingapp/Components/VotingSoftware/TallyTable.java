package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class TallyTable {
    public Map<Integer, Integer> tallyTable;

    public TallyTable() {
        tallyTable = new HashMap<>();
    }

    public void addTally(Integer id, Integer numOfVotes) {
        tallyTable.put(id, numOfVotes);
    }

}
