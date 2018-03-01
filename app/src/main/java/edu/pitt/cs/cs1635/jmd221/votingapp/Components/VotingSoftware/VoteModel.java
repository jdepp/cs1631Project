package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

/**
 * Created by jeremydeppen on 2/27/18.
 */

public class VoteModel {

    public String phoneNumber;
    public int voteNumber;

    public VoteModel(String num, int vote) {
        phoneNumber = num;
        voteNumber = vote;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

}
