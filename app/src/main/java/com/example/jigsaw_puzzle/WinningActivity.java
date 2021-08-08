package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WinningActivity extends AppCompatActivity {
    TextView scoreTextView;
    Button playAgain;
    Button leaderboard;
    Button redirectToMenu;
    Intent leaderboardIntent;
    Intent homeIntent;
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
        redirectToMenu = findViewById(R.id.redirectHome);
        scoreTextView = findViewById(R.id.scoreTextView);
        leaderboardIntent = new Intent(this,Leaderboard.class);
        homeIntent = new Intent(this,HomeActivity.class);
        double score = getIntent().getDoubleExtra("score",0);
        String mode = getIntent().getStringExtra("mode");
        score = Math.round(score*100)/100.0;
        String yourScore = String.valueOf(score);
        scoreTextView.setText(String.valueOf(score));
        SharedPreferences preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
        String username = preferences.getString("username","");
        String text = "Hey, "+username+" .Your score is "+ score;
        scoreTextView.setText(text);
        RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest newStringRequest = new StringRequest(Request.Method.POST, leaderBoardUrl,
                response -> {
                    if (response.equals("Updated")) {
                        Toast.makeText(WinningActivity.this, "Successfully saved your score!", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.equals("Nohigh")){
                        Toast.makeText(WinningActivity.this, "Score is less than your high score", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    StringRequest newStringRequest1 = new StringRequest(Request.Method.POST, leaderBoardUrl,
                            response -> {
                                if (response.equals("Updated")) {
                                    Toast.makeText(WinningActivity.this, "Successfully saved your score!", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.equals("Nohigh")){
                                    Toast.makeText(WinningActivity.this, "Score is less than your high score", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error1 -> Toast.makeText(WinningActivity.this, "Oops, there was some error saving your scores", Toast.LENGTH_SHORT).show()) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> loginParams = new HashMap<>();
                            loginParams.put("username", username);
                            loginParams.put("score", yourScore);
                            return loginParams;
                        }
                    };
                    myQueue.add(newStringRequest1);
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> scoreParams = new HashMap<>();
                scoreParams.put("username",username);
                scoreParams.put("score",yourScore);
                return scoreParams;
            }
        };

        myQueue.add(newStringRequest);



        // A new string request
        RequestQueue myQueue1 = Volley.newRequestQueue(getApplicationContext());
        StringRequest postData = new StringRequest(Request.Method.POST, scoreUrl,
                response -> {
                    switch (response) {
                        case "Saved":
                            Toast.makeText(WinningActivity.this, "Saved scores!", Toast.LENGTH_SHORT).show();
                            break;
                        case "Not updated":
                            Toast.makeText(WinningActivity.this, "Server side error while saving score", Toast.LENGTH_SHORT).show();
                            break;
                        case "length 0":
                            Toast.makeText(WinningActivity.this, "zero len", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                },
                error -> {
                    StringRequest postData1 = new StringRequest(Request.Method.POST, scoreUrl,
                            response -> {
                                if (response.equals("Saved")) {
                                    Toast.makeText(WinningActivity.this, "Saved scores", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.equals("Not updated")){
                                    Toast.makeText(WinningActivity.this, "Server side error while saving score", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(WinningActivity.this, "Oops, there was some error", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error12 -> Toast.makeText(WinningActivity.this, "Oops, there was some error saving your scores", Toast.LENGTH_SHORT).show()) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> scoreParams = new HashMap<>();
                            scoreParams.put("name", username);
                            scoreParams.put("mode",mode);
                            scoreParams.put("score", yourScore);
                            return scoreParams;
                        }
                    };
                    myQueue.add(postData1);
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String>  scoreParams = new HashMap<>();
                scoreParams.put("name",username);
                scoreParams.put("mode",mode);
                scoreParams.put("score",yourScore);
                return scoreParams;
            }
        };
        myQueue1.add(postData);

        playAgain.setOnClickListener(v -> {
            switch (mode) {
                case "3x3":
                case "4x4":
                case "5x5":
                    playAgainIntent = new Intent(getApplicationContext(), GameActivity.class);
                    playAgainIntent.putExtra("name", username);
                    playAgainIntent.putExtra("mode","CameraMode");
                    startActivity(playAgainIntent);
                    finish();
                    break;
                case "custom":
                    playAgainIntent = new Intent(getApplicationContext(), CustomMode.class);
                    playAgainIntent.putExtra("name", username);
                    playAgainIntent.putExtra("mode","CameraMode");
                    startActivity(playAgainIntent);
                    finish();
                    break;
                case "special":
                    playAgainIntent = new Intent(getApplicationContext(), FilterMode.class);
                    playAgainIntent.putExtra("name", username);
                    playAgainIntent.putExtra("mode","CameraMode");
                    startActivity(playAgainIntent);
                    finish();
                    break;
            }

        });
        leaderboard.setOnClickListener(v -> startActivity(leaderboardIntent));
        redirectToMenu.setOnClickListener(v -> {
            homeIntent.putExtra("name",username);
            startActivity(homeIntent);
            finish();
        });
    }
}