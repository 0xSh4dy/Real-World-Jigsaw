package com.example.jigsaw_puzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class SelfieMode extends AppCompatActivity {
    private Button x3Selfie;
    private Button x4Selfie;
    private Button x5Selfie;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView timeTV;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "You need to give access to camera in order to play this mode", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_mode);
        Activity act = this;
        x3Selfie = findViewById(R.id.x3Selfie);
        x4Selfie = findViewById(R.id.x4Selfie);
        x5Selfie = findViewById(R.id.x5Selfie);
        timeTV = findViewById(R.id.timeTV);
        fragmentManager=getSupportFragmentManager();
        if(findViewById(R.id.selfieFragment)!=null){
            if(savedInstanceState!=null){
                // If the activity is resumed i.e. the fragment has been added already
                return;
            }

        }
        x3Selfie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(act,new String[]{Manifest.permission.CAMERA},1);

                }
                else if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                x3Selfie.setVisibility(View.GONE);
                x4Selfie.setVisibility(View.GONE);
                x5Selfie.setVisibility(View.GONE);
                fragmentTransaction = fragmentManager.beginTransaction();
                ThreexSelfie threexSelfie = new ThreexSelfie();
                fragmentTransaction.add(R.id.selfieFragment,threexSelfie,null);
                fragmentTransaction.commit();
            }
            }
        });
        x4Selfie.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
            x3Selfie.setVisibility(View.GONE);
            x4Selfie.setVisibility(View.GONE);
            x5Selfie.setVisibility(View.GONE);
            fragmentTransaction = fragmentManager.beginTransaction();
            Fourxselfie fourxselfie = new Fourxselfie();
            fragmentTransaction.add(R.id.selfieFragment,fourxselfie,null);
            fragmentTransaction.commit();
            }
        });
        x5Selfie.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                x3Selfie.setVisibility(View.GONE);
                x4Selfie.setVisibility(View.GONE);
                x5Selfie.setVisibility(View.GONE);
                fragmentTransaction = fragmentManager.beginTransaction();
                Fivexselfie fivexselfie = new Fivexselfie();
                fragmentTransaction.add(R.id.selfieFragment,fivexselfie,null);
                fragmentTransaction.commit();
            }
        });

    }
}