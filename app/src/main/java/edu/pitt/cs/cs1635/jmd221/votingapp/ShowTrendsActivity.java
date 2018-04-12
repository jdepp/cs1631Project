package edu.pitt.cs.cs1635.jmd221.votingapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.Database;
import edu.pitt.cs.cs1635.jmd221.votingapp.Components.Database.GetAllCandidatesListener;

public class ShowTrendsActivity extends AppCompatActivity implements GetAllCandidatesListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trends);

        final TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
        final Context context = this;
        Database db = new Database();
        db.setGetYearsListener(new GetAllCandidatesListener() {
            @Override
            public void getCandidates(List<Candidate> candidates) {
                for (Candidate currentCandidate : candidates) {
//                    TextView tv = new TextView(context);
//                    String s = currentCandidate.getId() + " " + currentCandidate.getName() + " " + " " + currentCandidate.getNumVotes() + " " + currentCandidate.getYear() + " " + currentCandidate.getSubject();
//                    tv.setText(s);
//                    LinearLayout ll = (LinearLayout)findViewById(R.id.candidatesTest);
//                    ll.addView(tv);

                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    TableRow tableRow = new TableRow(context);
                    tableRow.setPadding(5, 5, 5, 5);
                    tableRow.setBackgroundColor(Color.parseColor("#ffffff"));

                    TextView idTextView = new TextView(context);
                    idTextView.setText(currentCandidate.getId());
                    idTextView.setLayoutParams(layoutParams);
                    idTextView.setGravity(Gravity.CENTER);

                    TextView nameTextView = new TextView(context);
                    nameTextView.setText(currentCandidate.getName());
                    nameTextView.setLayoutParams(layoutParams);
                    nameTextView.setGravity(Gravity.CENTER);

                    TextView subjectTextView = new TextView(context);
                    subjectTextView.setText(currentCandidate.getSubject());
                    subjectTextView.setLayoutParams(layoutParams);
                    subjectTextView.setGravity(Gravity.CENTER);

                    TextView yearTextView = new TextView(context);
                    yearTextView.setText(currentCandidate.getYear());
                    yearTextView.setLayoutParams(layoutParams);
                    yearTextView.setGravity(Gravity.CENTER);

                    TextView numVotesTextView = new TextView(context);
                    numVotesTextView.setText(currentCandidate.getNumVotes());
                    numVotesTextView.setLayoutParams(layoutParams);
                    numVotesTextView.setGravity(Gravity.CENTER);

                    tableRow.addView(idTextView);
                    tableRow.addView(nameTextView);
                    tableRow.addView(subjectTextView);
                    tableRow.addView(yearTextView);
                    tableRow.addView(numVotesTextView);

                    table.addView(tableRow, 1);

                }
            }
        });

    }

    public void getCandidates(List<Candidate> candidateList) { }
}