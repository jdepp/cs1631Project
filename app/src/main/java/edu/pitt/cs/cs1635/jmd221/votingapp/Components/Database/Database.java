package edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.cs.cs1635.jmd221.votingapp.Candidate;


/* Classes used to abstract out the database calls */
public class Database {

    private FirebaseDatabase database;
    private GetAllCandidatesListener mListener;

    public Database() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference yearsReference = database.getReference("year");

        /* Async loads in all the years (and hence the candidates for that year), and sends a List of Candidates
         * for all years to whichever class/activity implements the GetYearsListener to be handled */
        yearsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Candidate> candidates = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for(DataSnapshot cand : child.getChildren()) {
                        candidates.add(cand.getValue(Candidate.class));
                    }
                }
                if (mListener != null)
                    mListener.getCandidates(candidates);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    // Adds the new Candidate under the id and then under the year it is associated with
    public void createCandidate(Candidate newCandidate) {
        database.getReference().child("year").child(newCandidate.getYear()).child(newCandidate.getId()).setValue(newCandidate);
    }

    public void deleteClass(Candidate courseToBeDeleted) {
        database.getReference("year").child(courseToBeDeleted.getYear()).child(courseToBeDeleted.getId()).removeValue();
    }


    /* Listener to be used in fragments/activities that implement this class */
    public void setGetYearsListener(GetAllCandidatesListener eventListener) {
        this.mListener = eventListener;
    }

}
