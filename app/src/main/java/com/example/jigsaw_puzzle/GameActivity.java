package com.example.jigsaw_puzzle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Intent cameraCapture;
    private FragmentManager fragmentManager;
    private TextView timeTextView;
    int timeElapsed;
    int clickedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("The Real Jigsaw");
        cameraCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Button threeXButton = findViewById(R.id.threeX);
        Button fourXButton = findViewById(R.id.fourX);
        Button fiveXButton = findViewById(R.id.fiveX);
        fragmentManager= getSupportFragmentManager();
        timeTextView = findViewById(R.id.timeTextView);
        timeTextView.setVisibility(View.GONE);
        CardView cardView;

        String mode = getIntent().getStringExtra("mode");
        timeElapsed=0;
        threeXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedBtn=3;
                if(mode.equals("CameraMode")){
                    threeXButton.setVisibility(View.GONE);
                    fourXButton.setVisibility(View.GONE);
                    fiveXButton.setVisibility(View.GONE);
                    OpenCamera();
                    timeTextView.setVisibility(View.VISIBLE);

                }
            }
        });
        fourXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedBtn=4;
                if(mode.equals("CameraMode")){
                    threeXButton.setVisibility(View.GONE);
                    fourXButton.setVisibility(View.GONE);
                    fiveXButton.setVisibility(View.GONE);
                OpenCamera();
                }
            }
        });
        fiveXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedBtn=5;
                if(mode.equals("CameraMode")){
                    threeXButton.setVisibility(View.GONE);
                    fourXButton.setVisibility(View.GONE);
                    fiveXButton.setVisibility(View.GONE);
                OpenCamera();
                }
            }
        });

    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap photoCapture = (Bitmap)data.getExtras().get("data");
                        // Convert the captured bitmap into a byte array to send it to the fragment
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photoCapture.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] byteArray = stream.toByteArray();
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("image",byteArray);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ThreeXFragment threeXFragment = new ThreeXFragment();
                        FourxFragment fourxFragment = new FourxFragment();
                        FivexFragment fivexFragment = new FivexFragment();
                        if(clickedBtn==3){
                        fragmentTransaction.add(R.id.fragmentCont1,threeXFragment,null);
                        threeXFragment.setArguments(bundle);
                        Toast.makeText(GameActivity.this, "3x3 world mode started!", Toast.LENGTH_SHORT).show();

                        }
                        else if(clickedBtn==4){
                            fragmentTransaction.add(R.id.fragmentCont1,fourxFragment,null);
                            fourxFragment.setArguments(bundle);
                            Toast.makeText(GameActivity.this, "4x4 world mode started!", Toast.LENGTH_SHORT).show();

                        }
                        else if(clickedBtn==5){
                            fragmentTransaction.add(R.id.fragmentCont1,fivexFragment,null);
                            fivexFragment.setArguments(bundle);
                            Toast.makeText(GameActivity.this, "5x5 world mode started", Toast.LENGTH_SHORT).show();
                        }
                        fragmentTransaction.commit();


                    }
                }
            });
    public void OpenCamera() {
            resultLauncher.launch(cameraCapture);
    }


}