package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;

public class HomeActivity extends AppCompatActivity {
    private TextView nameTextView;
    SharedPreferences preferences;
    private Intent realModeIntent;
    private Intent selfieModeIntent;
    private Intent customModeIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nameTextView = findViewById(R.id.nameTextView);
        preferences = getSharedPreferences("userData",MODE_PRIVATE);
        String username = preferences.getString("username"," user");
        username = "Hello " + username;
        nameTextView.setText(username);
        realModeIntent = new Intent(getApplicationContext(),RealWorld.class);
        selfieModeIntent = new Intent(getApplicationContext(),SelfieMode.class);
        customModeIntent = new Intent(getApplicationContext(),CustomMode.class);
    }
    public void StartRealMode(View view){
        startActivity(realModeIntent);
    }
    public void StartSelfieMode(View view){
        startActivity(selfieModeIntent);
    }
    public void StartCustomMode(View view){
        startActivity(customModeIntent);
    }
}