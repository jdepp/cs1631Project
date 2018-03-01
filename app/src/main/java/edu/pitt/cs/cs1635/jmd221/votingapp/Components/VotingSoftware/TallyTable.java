package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.ArrayList;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class TallyTable {
    public ArrayList<TallyModel> tallyTable;

    public TallyTable() {
        tallyTable = new ArrayList<>();
    }

    public void addTally(TallyModel tally) {
        tallyTable.add(tally);
    }

    public ArrayList<TallyModel> getTallyTable() {
        return tallyTable;
    }

}
