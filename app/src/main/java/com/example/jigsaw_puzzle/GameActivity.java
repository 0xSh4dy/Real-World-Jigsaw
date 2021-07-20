package com.example.jigsaw_puzzle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Button threeXButton;
    private Button fourXButton;
    private Button fiveXButton;
    private Intent cameraCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cameraCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        threeXButton = findViewById(R.id.threeX);
        fourXButton = findViewById(R.id.fourX);
        fiveXButton = findViewById(R.id.fiveX);
        String mode = getIntent().getStringExtra("mode");

        threeXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("CameraMode")){
                OpenCamera();
                }
            }
        });
        fourXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("CameraMode")){
                OpenCamera();
                }
            }
        });
        fiveXButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        final int imageHeight = photoCapture.getHeight();
                        final int imageWidth = photoCapture.getWidth();
                        ArrayList<Bitmap>smallBitmaps = new ArrayList<Bitmap>();
                        int x=0;
                        int y=0;
                        for(int i=0;i<9;i++){
                         Bitmap bitmap = Bitmap.createBitmap(photoCapture,x,y,imageWidth/3,imageHeight/3);
                            if(i==2||i==5){
                                y+=imageHeight/3;
                                x=0;
                            }
                            else{
                                x+=imageWidth/3;
                            }
                            smallBitmaps.add(bitmap);


                        }

                    }
                }
            });
    public void OpenCamera() {
            resultLauncher.launch(cameraCapture);
    }
}