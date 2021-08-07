package com.example.jigsaw_puzzle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
@SuppressLint("ClickableViewAccessibility")

public class CustomMode extends AppCompatActivity {
    Button startCustom;
    Button changeBg;
    Bitmap bm1,bm2,bm3,bm4,bm5;
    ImageView customImage;
    TextView preview;
    Bitmap appliedBitmap;
    Bitmap bmp;
    TextView countMoves;
    String uname;
    Chronometer chron;
    ArrayList<Bitmap> finalDividedBitmaps;
    int n_moves=0;
    ImageView ic1,ic2,ic3,ic4,ic5,ic6,ic7,ic8,ic9;
    ImageView[] imageViewsAlt;
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
        countMoves = findViewById(R.id.countMoves);
        countMoves.setVisibility(View.INVISIBLE);
        chron = findViewById(R.id.chron);
        chron.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
        uname = preferences.getString("username","");
        ic1 = findViewById(R.id.ic1);
        ic2 = findViewById(R.id.ic2);
        ic3 = findViewById(R.id.ic3);
        ic4 = findViewById(R.id.ic4);
        ic5 = findViewById(R.id.ic5);
        ic6 = findViewById(R.id.ic6);
        ic7 = findViewById(R.id.ic7);
        ic8 = findViewById(R.id.ic8);
        ic9 = findViewById(R.id.ic9);
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
                startCustom.setVisibility(View.GONE);
                changeBg.setVisibility(View.GONE);
                countMoves.setVisibility(View.VISIBLE);
                preview.setVisibility(View.GONE);
                chron.setVisibility(View.VISIBLE);
                chron.setBase(SystemClock.elapsedRealtime());
                chron.start();
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
                finalDividedBitmaps = smallBitmaps;
                imp.shuffleImages(imageViews,3,smallBitmaps);
                Intent winIntent = new Intent(getApplicationContext(),WinningActivity.class);
                imageViewsAlt = imageViews;
                customImage.setVisibility(View.INVISIBLE);
                for(ImageView img:imageViews){

                    img.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
                                               final String tag  = img.getTag().toString();
                                               final int tagInt = Integer.parseInt(tag);
                                               Bitmap bm1;
                                               @Override
                                               public void onSwipeTop() {
                                                   super.onSwipeTop();
                                                   if(tagInt>=4 && tagInt<=9){
                                                       switch(tagInt){
                                                           case 4:
                                                               bmp = ((BitmapDrawable)ic1.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               ic1.setImageBitmap(bm1);
                                                               img.setImageBitmap(bmp);
                                                               break;
                                                           case 5:
                                                               bmp = ((BitmapDrawable)ic2.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic2.setImageBitmap(bm1);
                                                               break;
                                                           case 6:
                                                               bmp = ((BitmapDrawable)ic3.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic3.setImageBitmap(bm1);
                                                               break;
                                                           case 7:
                                                               bmp = ((BitmapDrawable)ic4.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic4.setImageBitmap(bm1);
                                                               break;
                                                           case 8:
                                                               bmp = ((BitmapDrawable)ic5.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic5.setImageBitmap(bm1);
                                                               break;
                                                           case 9:
                                                               bmp = ((BitmapDrawable)ic6.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic6.setImageBitmap(bm1);
                                                               break;
                                                       }
                                                       n_moves++;
                                                       String txt = "Moves: "+n_moves;
                                                       countMoves.setText(txt);
                                                       int victory = 0;
                                                       for(int j=0;j<9;j++){
                                                           Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                                           if(bms == finalDividedBitmaps.get(j)){
                                                               victory++;
                                                           }
                                                           if(victory==9){
                                                               double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                               winIntent.putExtra("score",score);
                                                               winIntent.putExtra("username",uname);
                                                               winIntent.putExtra("mode","custom");
                                                               startActivity(winIntent);
                                                               finish();
                                                           }

                                                       }
                                                   }

                                               }

                                               @Override
                                               public void onSwipeBottom() {
                                                   super.onSwipeBottom();
                                                   if(tagInt>=1 && tagInt<=6){
                                                       switch(tagInt){
                                                           case 1:
                                                               bmp = ((BitmapDrawable)ic4.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic4.setImageBitmap(bm1);
                                                               break;
                                                           case 2:
                                                               bmp = ((BitmapDrawable)ic5.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic5.setImageBitmap(bm1);
                                                               break;
                                                           case 3:
                                                               bmp = ((BitmapDrawable)ic6.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic6.setImageBitmap(bm1);
                                                               break;
                                                           case 4:
                                                               bmp = ((BitmapDrawable)ic7.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic7.setImageBitmap(bm1);
                                                               break;
                                                           case 5:
                                                               bmp = ((BitmapDrawable)ic8.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic8.setImageBitmap(bm1);
                                                               break;
                                                           case 6:
                                                               bmp = ((BitmapDrawable)ic9.getDrawable()).getBitmap();
                                                               bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                               img.setImageBitmap(bmp);
                                                               ic9.setImageBitmap(bm1);
                                                               break;
                                                       }
                                                       n_moves++;
                                                       String txt = "Moves: "+n_moves;
                                                       countMoves.setText(txt);
                                                       int victory = 0;
                                                       for(int j=0;j<9;j++){
                                                           Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                                           if(bms == finalDividedBitmaps.get(j)){
                                                               victory++;
                                                           }
                                                           if(victory==9){
                                                               double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                               winIntent.putExtra("score",score);
                                                               winIntent.putExtra("username",uname);
                                                               winIntent.putExtra("mode","custom");
                                                               startActivity(winIntent);
                                                               finish();
                                                           }

                                                       }
                                                   }

                                               }

                                               @Override
                                               public void onSwipeRight() {
                                                   super.onSwipeRight();
                                                   switch(tagInt){
                                                       case 1:
                                                           bmp = ((BitmapDrawable)ic2.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic2.setImageBitmap(bm1);
                                                           break;
                                                       case 2:
                                                           bmp = ((BitmapDrawable)ic3.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic3.setImageBitmap(bm1);
                                                           break;
                                                       case 4:
                                                           bmp = ((BitmapDrawable)ic5.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic5.setImageBitmap(bm1);
                                                           break;
                                                       case 5:
                                                           bmp = ((BitmapDrawable)ic6.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic6.setImageBitmap(bm1);
                                                           break;
                                                       case 7:
                                                           bmp = ((BitmapDrawable)ic8.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic8.setImageBitmap(bm1);
                                                           break;
                                                       case 8:
                                                           bmp = ((BitmapDrawable)ic9.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic9.setImageBitmap(bm1);
                                                           break;
                                                   }
                                                   n_moves++;
                                                   String txt = "Moves: "+n_moves;
                                                   countMoves.setText(txt);
                                                   int victory = 0;
                                                   for(int j=0;j<9;j++){
                                                       Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                                       if(bms == finalDividedBitmaps.get(j)){
                                                           victory++;
                                                       }
                                                       if(victory==9){
                                                           double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                           winIntent.putExtra("score",score);
                                                           winIntent.putExtra("username",uname);
                                                           winIntent.putExtra("mode","custom");
                                                           startActivity(winIntent);
                                                           finish();
                                                       }

                                                   }
                                               }

                                               @Override
                                               public void onSwipeLeft() {
                                                   super.onSwipeLeft();
                                                   switch(tagInt){
                                                       case 2:
                                                           bmp = ((BitmapDrawable)ic1.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic1.setImageBitmap(bm1);
                                                           break;
                                                       case 3:
                                                           bmp = ((BitmapDrawable)ic2.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic2.setImageBitmap(bm1);
                                                           break;
                                                       case 5:
                                                           bmp = ((BitmapDrawable)ic4.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic4.setImageBitmap(bm1);
                                                           break;
                                                       case 6:
                                                           bmp = ((BitmapDrawable)ic5.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic5.setImageBitmap(bm1);
                                                           break;
                                                       case 8:
                                                           bmp = ((BitmapDrawable)ic7.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic7.setImageBitmap(bm1);
                                                           break;
                                                       case 9:
                                                           bmp = ((BitmapDrawable)ic8.getDrawable()).getBitmap();
                                                           bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                           img.setImageBitmap(bmp);
                                                           ic8.setImageBitmap(bm1);
                                                           break;
                                                   }
                                                   n_moves++;
                                                   String txt = "Moves: "+n_moves;
                                                   countMoves.setText(txt);
                                                   int victory = 0;
                                                   for(int j=0;j<9;j++){
                                                       Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                                       if(bms == finalDividedBitmaps.get(j)){
                                                           victory++;
                                                       }
                                                       if(victory==9){
                                                           double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                           winIntent.putExtra("score",score);
                                                           winIntent.putExtra("username",uname);
                                                           winIntent.putExtra("mode","custom");
                                                           startActivity(winIntent);
                                                           finish();
                                                       }

                                                   }
                                               }

                                           }

                    );


                }
            }


        });
        changeBg.setOnClickListener(v -> {
            i = (i+1)%5;
            appliedBitmap = arr.get(i);
            customImage.setImageBitmap(arr.get(i));
        });
    }
}