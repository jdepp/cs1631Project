package edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.*;
import java.util.*;

/* Class that keeps track of the tallies in the form of a HashMap<id of candidate, number of votes> */
public class TallyTable {
    private Map<Integer, Integer> hashMap;

    // Constructor that initializes the HashMap
    public TallyTable() {
        hashMap = new HashMap<>();
    }

    // Adds tally to HashMap
    public void addTally(Integer id, Integer numOfVotes) {
        hashMap.put(id, numOfVotes);
        hashMap = sortDescending(hashMap);
    }

    // Checks if the candidate is already in the HashMap
    public boolean isInTallyTable(int id) {
        if(hashMap.containsKey(id))  return true;
        else  return false;
    }

    // Gets the number of votes based off of the candidate's ID
    public int getNumberOfVotes(int id) {
        return hashMap.get(id);
    }

    // Returns the HashMap
    public Map<Integer, Integer> getHashMap() {
        return hashMap;
    }

    // Sorts the HashMap in descending order
    private Map<Integer, Integer> sortDescending(Map<Integer, Integer> unsortMap) {
        List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<Integer, Integer>>()
        {
            public int compare(Entry<Integer, Integer> o1,
                               Entry<Integer, Integer> o2)
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        for (Entry<Integer, Integer> entry : list)
            sortedMap.put(entry.getKey(), entry.getValue());

        return sortedMap;
    }

}
