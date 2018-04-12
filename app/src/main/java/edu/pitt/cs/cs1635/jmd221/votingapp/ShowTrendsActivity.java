package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.Database;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.GetAllCandidatesListener;

public class ShowTrendsActivity extends AppCompatActivity implements GetAllCandidatesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trends);

        final Context context = this;
        Database db = new Database();
        db.setGetYearsListener(new GetAllCandidatesListener() {
            @Override
            public void getCandidates(List<Candidate> candidates) {
                for (Candidate currentCandidate : candidates) {
                    TextView tv = new TextView(context);
                    String s = currentCandidate.getId() + " " + currentCandidate.getName() + " " + " " + currentCandidate.getNumVotes() + " " + currentCandidate.getYear() + " " + currentCandidate.getSubject();
                    tv.setText(s);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.candidatesTest);
                    ll.addView(tv);
                }
            }
        });

    }

    public void getCandidates(List<Candidate> candidateList) { }
}
