package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WinningActivity extends AppCompatActivity {
    TextView scoreTextView;
    Button playAgain;
    Button leaderboard;
    Button logout;
    Intent leaderboardIntent;
    Intent logoutIntent;
    Intent playAgainIntent;
    final String leaderBoardUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        playAgain = findViewById(R.id.playAgain);
        leaderboard = findViewById(R.id.leaderboard);
        logout = findViewById(R.id.logout);
        scoreTextView = findViewById(R.id.scoreTextView);
        leaderboardIntent = new Intent(this,Leaderboard.class);
        logoutIntent = new Intent(this,MainActivity.class);
        playAgainIntent = new Intent(this, HomeActivity.class);
        double score = getIntent().getDoubleExtra("score",0);
        score = Math.round(score*100)/100.0;
        scoreTextView.setText(String.valueOf(score));
        String username = getIntent().getStringExtra("username");
        String text = "Hey, "+username+" .Your score is "+ score;
        scoreTextView.setText(text);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(playAgainIntent);
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(leaderboardIntent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logoutIntent);
            }
        });
    }
}