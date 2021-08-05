package com.example.jigsaw_puzzle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class CustomMode extends AppCompatActivity {
    Button startCustom;
    Button changeBg;
    Bitmap bm1,bm2,bm3,bm4,bm5;
    ImageView customImage;
    TextView preview;
    Chronometer countUp;
    Bitmap appliedBitmap;
    TextView countMoves;
    int n_moves=0;
    ImageView ic1,ic2,ic3,ic4,ic5,ic6,ic7,ic8,ic9;
    int timeElapsed=0;
    int i=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
        startCustom = findViewById(R.id.startCustom);
        changeBg = findViewById(R.id.Change);
        customImage = findViewById(R.id.imageCustom);
        preview = findViewById(R.id.preview);
        countUp = findViewById(R.id.countUp);
        ic1 = findViewById(R.id.ic1);
        ic2 = findViewById(R.id.ic2);
        ic3 = findViewById(R.id.ic3);
        ic4 = findViewById(R.id.ic4);
        ic5 = findViewById(R.id.ic5);
        ic6 = findViewById(R.id.ic6);
        ic7 = findViewById(R.id.ic7);
        ic8 = findViewById(R.id.ic8);
        ic9 = findViewById(R.id.ic9);
        countMoves = findViewById(R.id.countMoves);
        ImageView []imageViews = {ic1,ic2,ic3,ic4,ic5,ic6,ic7,ic8,ic9};
        ImpFunctions imp = new ImpFunctions();
        bm1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.custom1);
        bm2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.custom2);
        bm3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.custom3);
        bm4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.custom4);
        bm5 = BitmapFactory.decodeResource(this.getResources(),R.drawable.custom5);
        ArrayList<Bitmap> arr = new ArrayList<>();
        arr.add(bm1);arr.add(bm2);arr.add(bm3);arr.add(bm4);arr.add(bm5);
        customImage.setImageBitmap(arr.get(0));
        appliedBitmap = arr.get(0);
        startCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.setVisibility(View.GONE);
                startCustom.setVisibility(View.GONE);
                changeBg.setVisibility(View.GONE);
                countUp.start();
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        timeElapsed++;
                    }
                },0,1000);
                // Splitting the bitmaps
                int height = appliedBitmap.getHeight();
                int width = appliedBitmap.getWidth();
                ArrayList<Bitmap> smallBitmaps = imp.splitBitmap(appliedBitmap,3,height/3,width/3,width,height);
                imp.shuffleImages(imageViews,3,smallBitmaps);

            }


        });
        changeBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = (i+1)%5;
                appliedBitmap = arr.get(i);
                customImage.setImageBitmap(arr.get(i));
            }
        });
    }
}