package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* Class that keeps track of the tallies in the form of a HashMap<id of candidate, number of votes> */
public class CandidateTable implements Serializable{
    private Map<String, String> hashMap;

    // Constructor that initializes the HashMap
    public CandidateTable() {
        hashMap = new HashMap<>();
    }

    // Adds tally to HashMap
    public void addCandidate(String id, String candidateName) {
        if(!hashMap.containsKey(id)) {
            hashMap.put(id, candidateName);
        }
    }

    // Checks if the candidate is already in the HashMap
    public boolean isInCandidateTable(String id) {
        if(hashMap.containsKey(id))  return true;
        else  return false;
    }

    public String getCandidateName(String id) {
        return hashMap.get(id);
    }

    // Returns the HashMap
    public Map<String, String> getHashMap() {
        return hashMap;
    }

}
