package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.*;
import java.util.*;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.Database;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.VotingSoftware.CandidateTable;

/* Activity that launches after user enters correct password in MainActivity.
 * This is where user will enter a list of Candidates to be voted on */
public class AddCandidatesActivity extends AppCompatActivity {
    private CandidateTable candidates;
    private String candidateSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidates);
        candidates = new CandidateTable();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);

        final EditText candidateNameInput = (EditText)findViewById(R.id.enterCandidateName);
        final EditText candidateNameIDInput = (EditText)findViewById(R.id.enterCandidateID);
        final Button addCandidateButton = (Button)findViewById(R.id.addCandidateButton);
        addCandidateButton.setEnabled(false);

        // Listen for text input changes and make "Add Candidate" button disabled if either text field is blank
        candidateNameInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0 || candidateNameIDInput.getText().toString().trim().length() == 0)
                    addCandidateButton.setEnabled(false);
                else
                    addCandidateButton.setEnabled(true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
        candidateNameIDInput.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0 || candidateNameInput.getText().toString().trim().length() == 0)
                    addCandidateButton.setEnabled(false);
                else
                    addCandidateButton.setEnabled(true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
    }

    // Transitions to PollingActivity and passes it the CandidateTable
    public void onStartPolling(View view) {
        Intent intent = new Intent(AddCandidatesActivity.this, PollingActivity.class);
        startActivity(intent);
    }

    // Adds the inputted candidate to CandidateTable
    public void onAddCandidate(View view) {
        EditText candidateNameEditText = (EditText)findViewById(R.id.enterCandidateName);
        EditText candidateIDEditText = (EditText)findViewById(R.id.enterCandidateID);
        EditText candidateSubjectEditText = (EditText)findViewById(R.id.enterCandidateSubject);
        String candidateName = candidateNameEditText.getText().toString();
        String candidateID = candidateIDEditText.getText().toString();
        candidateSubject = candidateSubjectEditText.getText().toString();

        boolean duplicate;
        if(!candidates.isInCandidateTable(candidateID))
            duplicate = false;
        else
            duplicate = true;
        candidates.addCandidate(candidateID, candidateName);

        TextView displayCandidatesTextView = (TextView)findViewById(R.id.displayCandidatesTextView);
        String candidatesDisplayed = "";
        if(duplicate)
            candidatesDisplayed = displayCandidatesTextView.getText().toString();
        else {
            candidatesDisplayed = displayCandidatesTextView.getText().toString() + "Candidate #" + candidateID + ": \"" + candidates.getCandidateName(candidateID) + "\"\n";
            Database db = new Database();
            String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
            Candidate newCandidate = new Candidate(candidateID, candidateName, candidateSubject, currentYear);
            db.createCandidate(newCandidate);
        }
        displayCandidatesTextView.setText(candidatesDisplayed);

        candidateNameEditText.setText("");
        candidateIDEditText.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
