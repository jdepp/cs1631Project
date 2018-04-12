package edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database;

import java.util.List;

import edu.pitt.cs.cs1635.jmd221.votingapp.Candidate;


/* Interface to be implemented by whatever fragment/activity wants to read the courses of the current user from database */
public interface GetAllCandidatesListener {
    public void getCandidates(List<Candidate> candidates);
}
