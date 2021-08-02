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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Intent cameraCapture;
    private String buttonClicked;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cameraCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Button threeXButton = findViewById(R.id.threeX);
        Button fourXButton = findViewById(R.id.fourX);
        Button fiveXButton = findViewById(R.id.fiveX);
        fragmentManager= getSupportFragmentManager();
        CardView cardView;

        String mode = getIntent().getStringExtra("mode");

        threeXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = "threeXButton";
                if(mode.equals("CameraMode")){
                OpenCamera();
                }
            }
        });
        fourXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = "fourXButton";
                if(mode.equals("CameraMode")){
                OpenCamera();
                }
            }
        });
        fiveXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = "fiveXButton";
                if(mode.equals("CameraMode")){
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
                        if(buttonClicked.equals("threeXButton")){
                        fragmentTransaction.add(R.id.fragmentCont1,threeXFragment,null);
                        threeXFragment.setArguments(bundle);
                        fragmentTransaction.commit();
                        }
                        Toast.makeText(GameActivity.this, "done", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    public void OpenCamera() {
            resultLauncher.launch(cameraCapture);
    }

}