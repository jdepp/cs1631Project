package edu.pitt.cs.cs1635.jmd221.votingapp;

/* Candidate model class that keeps track of a Candidates: id, poster subject, num of votes for their poster, and year of poster */
public class Candidate {

    private String id;
    private String name;
    private String subject;
    private String numVotes;
    private String year;

    public Candidate() { }

    public Candidate(String i, String n, String sub, String y) {
        id = i;
        name = n;
        subject = sub;
        year = y;
        numVotes = "0";
    }

    public void setId(String i) { id = i; }
    public void setSubject(String sub) { subject = sub; }
    public void setNumVotes(String votes) { numVotes = votes; }
    public void setYear(String y) { year = y; }
    public void setName(String n) { name = n; }

    public String getId() { return id; }
    public String getSubject() { return subject; }
    public String getNumVotes() { return numVotes; }
    public String getYear() { return year; }
    public String getName() { return name; }

}
