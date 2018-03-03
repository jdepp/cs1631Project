package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.HashMap;
import java.util.Map;

/* Class that keeps track of the tallies in the form of a HashMap<id of canidate, number of votes> */
public class TallyTable {
    private Map<Integer, Integer> hashMap;

    // Constructor that initializes the HashMap
    public TallyTable() {
        hashMap = new HashMap<>();
    }

    // Adds tally to HashMap
    public void addTally(Integer id, Integer numOfVotes) {
        hashMap.put(id, numOfVotes);
    }

    // Checks if the canidate is already in the HashMap
    public boolean isInTallyTable(int id) {
        if(hashMap.containsKey(id))
            return true;
        else
            return false;
    }

    // Gets the number of votes based off of the canidate's ID
    public int getNumberOfVotes(int id) {
        return hashMap.get(id);
    }

    // Returns the HashMap
    public Map<Integer, Integer> getHashMap() {
        return hashMap;
    }

}
