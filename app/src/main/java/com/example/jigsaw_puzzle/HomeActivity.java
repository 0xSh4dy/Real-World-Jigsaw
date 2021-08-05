package com.example.jigsaw_puzzle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView nameTextView;
    SharedPreferences preferences;
    Intent GameActivity;
    Button cameraButton;
    Button customMode;
    Intent specialMode;
    Intent customIntent;
    public String uname="";
    Button specialBtn,logout1;
    ConnectivityManager cm;
    Intent leaderboard,logout;
    Button lead;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "You need to give access to camera in order to access this mode", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Menu");
        Activity thisActivity = this;
        final String loginUrl = "https://jigsaw-real.herokuapp.com/login";
        leaderboard = new Intent(this,Leaderboard.class);
        customMode = findViewById(R.id.customMode);
        customIntent = new Intent(this,CustomMode.class);
        lead = findViewById(R.id.lead);
        nameTextView = findViewById(R.id.nameTextView);
        cameraButton = findViewById(R.id.cameraMode);
        GameActivity = new Intent(this, GameActivity.class);
        specialMode = new Intent(this,FilterMode.class);
        specialBtn = findViewById(R.id.special);
        logout1 = findViewById(R.id.logout1);
        logout = new Intent(this,MainActivity.class);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(leaderboard);
                finish();
            }
        });
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logout);
                finish();
            }
        });
        customMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(customIntent);
            }
        });
        if(cm.getActiveNetworkInfo()!=null){
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            uname = response;
                            String greeting = "Welcome, " + uname;
                            nameTextView.setText(greeting);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            queue.add(request);
        }
        else{
            nameTextView.setText("No internet! Cannot find username");
        }
        specialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialMode.putExtra("username",uname);
                startActivity(specialMode);
            }
        });
    }

    public void checkCameraPermission(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            GameActivity.putExtra("mode", "CameraMode");
            GameActivity.putExtra("username",uname);
            startActivity(GameActivity);
        }


    }
}