package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WinningActivity extends AppCompatActivity {
    TextView scoreTextView;
    Button playAgain;
    Button leaderboard;
    Button logout;
    Intent leaderboardIntent;
    Intent logoutIntent;
    Intent playAgainIntent;
    final String leaderBoardUrl = "https://jigsaw-real.herokuapp.com/scoreboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        setTitle("The Real Jigsaw");
        playAgain = findViewById(R.id.playAgain);
        leaderboard = findViewById(R.id.leaderboard);
        logout = findViewById(R.id.logout);
        scoreTextView = findViewById(R.id.scoreTextView);
        leaderboardIntent = new Intent(this,Leaderboard.class);
        logoutIntent = new Intent(this,MainActivity.class);
        playAgainIntent = new Intent(this, HomeActivity.class);
        double score = getIntent().getDoubleExtra("score",0);
        score = Math.round(score*100)/100.0;
        String yourScore = String.valueOf(score);
        scoreTextView.setText(String.valueOf(score));
        String username = getIntent().getStringExtra("username");
        String text = "Hey, "+username+" .Your score is "+ score;
        scoreTextView.setText(text);
        RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest newStringRequest = new StringRequest(Request.Method.POST, leaderBoardUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Updated")) {
                            Toast.makeText(WinningActivity.this, "Successfully saved your score!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> loginParams = new HashMap<String,String>();
                loginParams.put("username",username);
                loginParams.put("score",yourScore);
                return loginParams;
            }
        };
        myQueue.add(newStringRequest);

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