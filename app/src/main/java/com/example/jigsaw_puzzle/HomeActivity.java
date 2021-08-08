package com.example.jigsaw_puzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {
    Intent GameActivity;
    Button cameraButton;
    Button customMode;
    Intent specialMode;
    Intent customIntent;
    public String uname="";
    Button specialBtn,logout1;
    ConnectivityManager cm;
    Intent leaderboard,logout;
    Button myScores;
    Button lead;
    // To find out whether World mode has been clicked or Special mode.
    int pressed;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "You need to give access to camera in order to access this mode", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Menu");
        SharedPreferences preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
        final String username = preferences.getString("username","");
        final String  loggedOut = preferences.getString("loggedOut","");
        leaderboard = new Intent(this,Leaderboard.class);
        customMode = findViewById(R.id.customMode);
        myScores = findViewById(R.id.myScores);
        customIntent = new Intent(this,CustomMode.class);
        lead = findViewById(R.id.lead);
        TextView nameTextView = findViewById(R.id.nameTextView);
        cameraButton = findViewById(R.id.cameraMode);
        GameActivity = new Intent(this, GameActivity.class);
        specialMode = new Intent(this,FilterMode.class);
        specialBtn = findViewById(R.id.special);
        logout1 = findViewById(R.id.logout1);
        logout = new Intent(this,MainActivity.class);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(loggedOut.equals("yes")){
            startActivity(logout);
        }

        uname = username;
        String un = "Welcome! "+username;
        nameTextView.setText(un);
        lead.setOnClickListener(v -> startActivity(leaderboard));
        logout1.setOnClickListener(v -> {
            SharedPreferences pref = this.getSharedPreferences("auth",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("loggedIn");
            editor.remove("username");
            editor.putString("loggedOut","yes");
            editor.apply();
            startActivity(logout);
            finish();
        });
        myScores.setOnClickListener(v -> {
            Intent myScoresIntent = new Intent(getApplicationContext(),MyScores.class);
            myScoresIntent.putExtra("username",username);
            startActivity(myScoresIntent);
        });
        customMode.setOnClickListener(v -> {
            customIntent.putExtra("username",uname);
            startActivity(customIntent);
        });
        cameraButton.setOnClickListener(v->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                GameActivity.putExtra("mode", "CameraMode");
                GameActivity.putExtra("username",uname);
                startActivity(GameActivity);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
            });
        specialBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                specialMode.putExtra("username",uname);
                startActivity(specialMode);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
            });
    }

}