package com.example.jigsaw_puzzle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ClickableViewAccessibility")
public class FilterMode extends AppCompatActivity {
    static{
        System.loadLibrary("NativeImageProcessor");
    }

    Button red,green,blue,brightness,starlit,vignette;
    Intent imageCapture,winIntent;
    ImageView imageView;
    ImageView im1,im2,im3,im4,im5,im6,im7,im8,im9;
    Bitmap photoCapture;
    Button startCapture,startGame;
    Bitmap finalImage,originalImage,copyImage;
    Button[]filters;
    ImageView[]imageViews;
    ImageView[]imageViewsAlt;
    ArrayList<Bitmap>smallBitmaps;
    Chronometer chronometer;
    TextView movesTV;
    Bitmap bmp;
    int n_moves=0;
    int timeElapsed=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_mode);
        imageCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        vignette = findViewById(R.id.vigenette);
        imageView = findViewById(R.id.imageView);
        startGame = findViewById(R.id.startGame);
        movesTV = findViewById(R.id.movesTV1);
        startCapture = findViewById(R.id.startFilterGame);
        brightness = findViewById(R.id.Brightness);
        starlit = findViewById(R.id.Starlit);
        chronometer = findViewById(R.id.chronoFilter);
        movesTV.setVisibility(View.INVISIBLE);
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        filters = new Button[]{red, green, blue, vignette, brightness, starlit};
        startGame.setVisibility(View.GONE);
        winIntent = new Intent(getApplicationContext(),WinningActivity.class);
        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        im4 = findViewById(R.id.im4);
        im5 = findViewById(R.id.im5);
        im6 = findViewById(R.id.im6);
        im7 = findViewById(R.id.im7);
        im8 = findViewById(R.id.im8);
        im9 = findViewById(R.id.im9);
        imageViews = new ImageView[]{im1,im2,im3,im4,im5,im6,im7,im8,im9};
        imageViewsAlt = imageViews;
        for(Button btn:filters){
            btn.setOnClickListener(v -> {
                String tag = v.getTag().toString();
                copyImage = photoCapture;
                switch(tag){
                    case "1":
                        Filter vignetteFilter = new Filter();
                        vignetteFilter.addSubFilter(new VignetteSubFilter(getApplicationContext(),100));
                        Bitmap otp = vignetteFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(otp);
                        finalImage = otp;
                        break;
                    case "2":
                        Filter redFilter = new Filter();
                        redFilter.addSubFilter(new ColorOverlaySubFilter(100,0.28f,0,0));
                        Bitmap outputImage = redFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(outputImage);
                        finalImage = outputImage;
                        break;
                    case "3":
                        Filter greenFilter = new Filter();
                        greenFilter.addSubFilter(new ColorOverlaySubFilter(100,0,0.28f,0));
                        Bitmap outputImage1 = greenFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(outputImage1);
                        finalImage = outputImage1;
                        break;
                    case "4":
                        Filter blueFilter = SampleFilters.getBlueMessFilter();
                        Bitmap outputImage2  = blueFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(outputImage2);
                        finalImage = outputImage2;
                        break;
                    case "5":
                        Filter nightFilter = SampleFilters.getNightWhisperFilter();
                        Bitmap outputImage3 = nightFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(outputImage3);
                        finalImage = outputImage3;
                        break;
                    case "6":
                        Filter starFilter = SampleFilters.getStarLitFilter();
                        Bitmap outputImage4 = starFilter.processFilter(photoCapture);
                        imageView.setImageBitmap(outputImage4);
                        finalImage = outputImage4;
                }
            });
        }

        startCapture.setOnClickListener(v -> {
            openCamera();
            startGame.setVisibility(View.VISIBLE);
            String txt = "Again?";
            startCapture.setText(txt);
        });
        startGame.setOnClickListener(v -> startTheGame());
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
                        photoCapture = (Bitmap)data.getExtras().get("data");
                        originalImage = photoCapture;
                        finalImage = photoCapture;
                        // Convert the captured bitmap into a byte array to send it to the fragment

                        imageView.setImageBitmap(photoCapture);


                    }
                }
            });
    public void openCamera(){
        resultLauncher.launch(imageCapture);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startTheGame(){
        movesTV.setVisibility(View.VISIBLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeElapsed++;
            }
        },0,1000);
        startCapture.setVisibility(View.GONE);
        startGame.setVisibility(View.GONE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        SharedPreferences preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
        String uname = preferences.getString("username","");
        for(Button btn:filters){
            btn.setVisibility(View.GONE);
        }
        ImpFunctions imp = new ImpFunctions();
        Bitmap bmx = finalImage;
        final int imageWidth = bmx.getWidth();
        final int imageHeight = bmx.getHeight();
        smallBitmaps = imp.splitBitmap(bmx,3,imageHeight/3,imageWidth/3,imageWidth,imageHeight);
        imp.shuffleImages(imageViews,3,smallBitmaps);
        ArrayList<Bitmap> finalDividedBitmaps = smallBitmaps;
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
                                                       bmp = ((BitmapDrawable)im1.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       im1.setImageBitmap(bm1);
                                                       img.setImageBitmap(bmp);
                                                       break;
                                                   case 5:
                                                       bmp = ((BitmapDrawable)im2.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im2.setImageBitmap(bm1);
                                                       break;
                                                   case 6:
                                                       bmp = ((BitmapDrawable)im3.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im3.setImageBitmap(bm1);
                                                       break;
                                                   case 7:
                                                       bmp = ((BitmapDrawable)im4.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im4.setImageBitmap(bm1);
                                                       break;
                                                   case 8:
                                                       bmp = ((BitmapDrawable)im5.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im5.setImageBitmap(bm1);
                                                       break;
                                                   case 9:
                                                       bmp = ((BitmapDrawable)im6.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im6.setImageBitmap(bm1);
                                                       break;
                                               }
                                           }
                                           n_moves++;
                                           String txt = "Moves: "+n_moves;
                                           movesTV.setText(txt);
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
                                                   winIntent.putExtra("mode","special");
                                                   startActivity(winIntent);
                                                   finish();
                                               }

                                           }
                                       }

                                       @Override
                                       public void onSwipeBottom() {
                                           super.onSwipeBottom();
                                           if(tagInt>=1 && tagInt<=6){
                                               switch(tagInt){
                                                   case 1:
                                                       bmp = ((BitmapDrawable)im4.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im4.setImageBitmap(bm1);
                                                       break;
                                                   case 2:
                                                       bmp = ((BitmapDrawable)im5.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im5.setImageBitmap(bm1);
                                                       break;
                                                   case 3:
                                                       bmp = ((BitmapDrawable)im6.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im6.setImageBitmap(bm1);
                                                       break;
                                                   case 4:
                                                       bmp = ((BitmapDrawable)im7.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im7.setImageBitmap(bm1);
                                                       break;
                                                   case 5:
                                                       bmp = ((BitmapDrawable)im8.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im8.setImageBitmap(bm1);
                                                       break;
                                                   case 6:
                                                       bmp = ((BitmapDrawable)im9.getDrawable()).getBitmap();
                                                       bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                       img.setImageBitmap(bmp);
                                                       im9.setImageBitmap(bm1);
                                                       break;
                                               }
                                           }
                                           n_moves++;
                                           String txt = "Moves: "+n_moves;
                                           movesTV.setText(txt);
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
                                                   winIntent.putExtra("mode","special");
                                                   startActivity(winIntent);
                                                   finish();
                                               }

                                           }
                                       }

                                       @Override
                                       public void onSwipeRight() {
                                           super.onSwipeRight();
                                           switch(tagInt){
                                               case 1:
                                                   bmp = ((BitmapDrawable)im2.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im2.setImageBitmap(bm1);
                                                   break;
                                               case 2:
                                                   bmp = ((BitmapDrawable)im3.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im3.setImageBitmap(bm1);
                                                   break;
                                               case 4:
                                                   bmp = ((BitmapDrawable)im5.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im5.setImageBitmap(bm1);
                                                   break;
                                               case 5:
                                                   bmp = ((BitmapDrawable)im6.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im6.setImageBitmap(bm1);
                                                   break;
                                               case 7:
                                                   bmp = ((BitmapDrawable)im8.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im8.setImageBitmap(bm1);
                                                   break;
                                               case 8:
                                                   bmp = ((BitmapDrawable)im9.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im9.setImageBitmap(bm1);
                                                   break;
                                           }
                                           n_moves++;
                                           String txt = "Moves: "+n_moves;
                                           movesTV.setText(txt);
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
                                                   winIntent.putExtra("mode","special");
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
                                                   bmp = ((BitmapDrawable)im1.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im1.setImageBitmap(bm1);
                                                   break;
                                               case 3:
                                                   bmp = ((BitmapDrawable)im2.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im2.setImageBitmap(bm1);
                                                   break;
                                               case 5:
                                                   bmp = ((BitmapDrawable)im4.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im4.setImageBitmap(bm1);
                                                   break;
                                               case 6:
                                                   bmp = ((BitmapDrawable)im5.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im5.setImageBitmap(bm1);
                                                   break;
                                               case 8:
                                                   bmp = ((BitmapDrawable)im7.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im7.setImageBitmap(bm1);
                                                   break;
                                               case 9:
                                                   bmp = ((BitmapDrawable)im8.getDrawable()).getBitmap();
                                                   bm1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                                                   img.setImageBitmap(bmp);
                                                   im8.setImageBitmap(bm1);
                                                   break;
                                           }
                                           n_moves++;
                                           String txt = "Moves: "+n_moves;
                                           movesTV.setText(txt);
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
                                                   winIntent.putExtra("mode","special");
                                                   startActivity(winIntent);
                                                   finish();
                                               }

                                           }
                                       }
                                   }

            );


        }
    }

}