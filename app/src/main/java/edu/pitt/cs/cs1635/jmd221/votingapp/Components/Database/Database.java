package edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.pitt.cs.cs1635.jmd221.votingapp.Candidate;


/* Classes used to abstract out the database calls */
public class Database {

    private FirebaseDatabase database;

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    // Adds the new Candidate under the id and then under the year it is associated with
    public void createCandidate(Candidate newCandidate) {
        database.getReference().child("year").child(newCandidate.getYear()).child(newCandidate.getId()).setValue(newCandidate);
    }

}
