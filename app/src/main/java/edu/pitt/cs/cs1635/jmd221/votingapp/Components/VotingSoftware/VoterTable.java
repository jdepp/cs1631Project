package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.HashMap;
import java.util.Map;

/* Class that keeps track of voter's phone numbers and their vote stored in a HashMap<voter's phone number, their vote> */
public class VoterTable {
    private Map<String, String> hashMap;

    // Constructor that initializes the HashMap
    public VoterTable() {
        hashMap = new HashMap<>();
    }

    // Adds the voter to the HashMap
    public void addVoter(String phoneNumber, String vote) {
        hashMap.put(phoneNumber, vote);
    }

    // Checks if the voter is already in the voter table
    public boolean isInVoterTable(String phoneNumber) {
        if(hashMap.containsKey(phoneNumber))  return true;
        else  return false;
    }

    // Returns the HashMap
    public Map<String, String> getHashMap() {
        return hashMap;
    }

}
