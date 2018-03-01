package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.ArrayList;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.VoteModel;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class VoterTable {
    public ArrayList<VoteModel> voterTable;

    public VoterTable() {
        voterTable = new ArrayList<>();
    }

    public void addVote(VoteModel vote) {
        if(!voterTable.contains(vote))
            voterTable.add(vote);
    }

    public ArrayList<VoteModel> getVoterTable() {
        return voterTable;
    }
}
