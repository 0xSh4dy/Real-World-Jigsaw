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
    final String scoreUrl = "https://jigsaw-real.herokuapp.com/scoreboard/users";
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
        String mode = getIntent().getStringExtra("mode");
        score = Math.round(score*100)/100.0;
        String yourScore = String.valueOf(score);
        double  scr = Double.parseDouble(yourScore);
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
                        }
                        else if(response.equals("Nohigh")){
                            Toast.makeText(WinningActivity.this, "Score is less than your high score", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StringRequest newStringRequest1 = new StringRequest(Request.Method.POST, leaderBoardUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("Updated")) {
                                            Toast.makeText(WinningActivity.this, "Successfully saved your score!", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.equals("Nohigh")){
                                            Toast.makeText(WinningActivity.this, "Score is less than your high score", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(WinningActivity.this, "Oops, there was some error saving your scores", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> loginParams = new HashMap<String, String>();
                                loginParams.put("username", username);
                                loginParams.put("score", yourScore);
                                return loginParams;
                            }
                        };
                        myQueue.add(newStringRequest1);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> scoreParams = new HashMap<String,String>();
                scoreParams.put("username",username);
                scoreParams.put("score",yourScore);
                return scoreParams;
            }
        };

        myQueue.add(newStringRequest);



        // A new string request
        RequestQueue myQueue1 = Volley.newRequestQueue(getApplicationContext());
        StringRequest postData = new StringRequest(Request.Method.POST, scoreUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Saved")) {
                            Toast.makeText(WinningActivity.this, "Saved scores!", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("Not updated")){
                            Toast.makeText(WinningActivity.this, "Server side error while saving score", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("length 0")){
                            Toast.makeText(WinningActivity.this, "zero len", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StringRequest postData1 = new StringRequest(Request.Method.POST, scoreUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("Saved")) {
                                            Toast.makeText(WinningActivity.this, "Saved scores", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.equals("Not updated")){
                                            Toast.makeText(WinningActivity.this, "Server side error while saving score", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(WinningActivity.this, "Oops, there was some error saving your scores", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> scoreParams = new HashMap<String, String>();
                                scoreParams.put("name", username);
                                scoreParams.put("mode",mode);
                                scoreParams.put("score", yourScore);
                                return scoreParams;
                            }
                        };
                        myQueue.add(postData1);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String>  scoreParams = new HashMap<String,String>();
                scoreParams.put("name",username);
                scoreParams.put("mode",mode);
                scoreParams.put("score",yourScore);
                return scoreParams;
            }
        };
        myQueue1.add(postData);

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
                finish();
            }
        });
    }
}