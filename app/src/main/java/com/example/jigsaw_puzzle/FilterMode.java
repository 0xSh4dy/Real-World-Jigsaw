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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    int timeElapsed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_mode);
        imageCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        vignette = findViewById(R.id.vigenette);
        imageView = findViewById(R.id.imageView);
        startGame = findViewById(R.id.startGame);
        startCapture = findViewById(R.id.startFilterGame);
        brightness = findViewById(R.id.Brightness);
        starlit = findViewById(R.id.Starlit);
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
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }

            });
        }

        startCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                startGame.setVisibility(View.VISIBLE);
                startCapture.setText("Again?");
            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                startTheGame();
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
                        photoCapture = (Bitmap)data.getExtras().get("data");
                        originalImage = photoCapture;
                        finalImage = photoCapture;
                        // Convert the captured bitmap into a byte array to send it to the fragment
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        photoCapture.compress(Bitmap.CompressFormat.PNG,100,stream);
//                        byte[] byteArray = stream.toByteArray();
//                        Bundle bundle = new Bundle();
//                        bundle.putByteArray("image",byteArray);
                        imageView.setImageBitmap(photoCapture);


                    }
                }
            });
    public void openCamera(){
        resultLauncher.launch(imageCapture);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startTheGame(){
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                timeElapsed++;
//            }
//        },0,1000);

        for(Button btn:filters){
            btn.setVisibility(View.GONE);
        }
        ImpFunctions imp = new ImpFunctions();
        Bitmap bm = finalImage;
        final int imageWidth = bm.getWidth();
        final int imageHeight = bm.getHeight();
        smallBitmaps = imp.splitBitmap(bm,3,imageHeight/3,imageWidth/3,imageWidth,imageHeight);
        int[]modifiedPositions =  imp.shuffleImages(imageViews,smallBitmaps);
//        for(ImageView img:imageViews){
//
//            ArrayList<Bitmap> finalDividedBitmaps = smallBitmaps;
//            img.setOnTouchListener(new OnSwipeTouchListener(this){
//
//                                       String tag = img.getTag().toString() ;
//                                       int tagInt = Integer.parseInt(tag) ;
//                                       Bitmap bmNew;
//                                       int exchangeTag;
//                                       Bitmap bm;
//                public void  onSwipeTop(){
//
//                    exchangeTag = tagInt-3;
//                    bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
//                    if(tag.equals("4") || tag.equals("5") || tag.equals("6")){
//
//                        if(exchangeTag==1){
//                            bmNew = ((BitmapDrawable)imageViews[0].getDrawable()).getBitmap();
//                            imageViews[0].setImageBitmap(bm);
//                            imageViews[3].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==2){
//                            bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
//                            imageViews[1].setImageBitmap(bm);
//                            imageViews[4].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==3){
//                            bmNew = ((BitmapDrawable)imageViews[2].getDrawable()).getBitmap();
//                            imageViews[2].setImageBitmap(bm);
//                            imageViews[5].setImageBitmap(bmNew);
//                        }
//
//                    }
//                    else if(tag.equals("7") || tag.equals("8") || tag.equals("9")){
//                        if(exchangeTag==4){
//                            bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
//                            imageViews[6].setImageBitmap(bmNew);
//                            imageViews[3].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==5){
//                            bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
//                            imageViews[7].setImageBitmap(bmNew);
//                            imageViews[4].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==6){
//                            bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
//                            imageViews[8].setImageBitmap(bmNew);
//                            imageViews[5].setImageBitmap(bm);
//                        }
//                    }
//                    int victory = 0;
//                    for(int j=0;j<9;j++){
//                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
//                        if(bms == finalDividedBitmaps.get(j)){
//                            victory++;
//                        }
//                        if(victory==9){
//                            double score = 10000/(timeElapsed*0.9);
//                            String uname = getIntent().getStringExtra("username");
//                            winIntent.putExtra("score",score);
//                            winIntent.putExtra("username",uname);
//                            startActivity(winIntent);
//                            finish();
//                        }
//
//                    }
//                }
//                public void onSwipeBottom(){
//
//
//                    bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
//                    exchangeTag = tagInt+3;
//                    if(tag.equals("1") || tag.equals("2") ||tag.equals("3")){
//                        if(exchangeTag==4){
//                            bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
//                            imageViews[0].setImageBitmap(bmNew);
//                            imageViews[3].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==5){
//                            bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
//                            imageViews[1].setImageBitmap(bmNew);
//                            imageViews[4].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==6){
//                            bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
//                            imageViews[2].setImageBitmap(bmNew);
//                            imageViews[5].setImageBitmap(bm);
//                        }
//                    }
//                    else if(tag.equals("4")|| tag.equals("5")|| tag.equals("6")){
//                        if(exchangeTag==7){
//                            bmNew = ((BitmapDrawable)imageViews[6].getDrawable()).getBitmap();
//                            imageViews[3].setImageBitmap(bmNew);
//                            imageViews[6].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==8){
//                            bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
//                            imageViews[4].setImageBitmap(bmNew);
//                            imageViews[7].setImageBitmap(bm);
//                        }
//                        else if(exchangeTag==9){
//                            bmNew = ((BitmapDrawable)imageViews[8].getDrawable()).getBitmap();
//                            imageViews[5].setImageBitmap(bmNew);
//                            imageViews[8].setImageBitmap(bm);
//                        }
//                    }int victory = 0;
//                    for(int j=0;j<9;j++){
//                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
//                        if(bms == finalDividedBitmaps.get(j)){
//                            victory++;
//
//                        }
//                        if(victory==9) {
//                            double score = 3*10000/(timeElapsed*0.9*2.85);
//                            String uname = getIntent().getStringExtra("username");
//                            winIntent.putExtra("score",score);
//                            winIntent.putExtra("username",uname);
//                            startActivity(winIntent);
//                            finish();
//                        }
//                    }
//                }
//                public void onSwipeLeft(){
//
//
//                    bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
//                    exchangeTag = tagInt-1;
//                    if(tag.equals("2")||tag.equals("5")||tag.equals("8")){
//                        if(exchangeTag==1){
//                            bmNew = ((BitmapDrawable)imageViews[0].getDrawable()).getBitmap();
//                            imageViews[0].setImageBitmap(bm);
//                            imageViews[1].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==4){
//                            bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
//                            imageViews[3].setImageBitmap(bm);
//                            imageViews[4].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==7){
//                            bmNew = ((BitmapDrawable)imageViews[6].getDrawable()).getBitmap();
//                            imageViews[6].setImageBitmap(bm);
//                            imageViews[7].setImageBitmap(bmNew);
//                        }
//                    }
//                    else if(tag.equals("3")||tag.equals("6")||tag.equals("9")){
//                        if(exchangeTag==2){
//                            bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
//                            imageViews[1].setImageBitmap(bm);
//                            imageViews[2].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==5){
//                            bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
//                            imageViews[4].setImageBitmap(bm);
//                            imageViews[5].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==8){
//                            bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
//                            imageViews[7].setImageBitmap(bm);
//                            imageViews[8].setImageBitmap(bmNew);
//                        }
//                    }
//                    int victory = 0;
//                    for(int j=0;j<9;j++){
//                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
//                        if(bms == finalDividedBitmaps.get(j)){
//                            victory++;
//                        }
//                        if(victory==9){
//                            double score = 3*10000/(timeElapsed*0.9*2.85);
//                            String uname = getIntent().getStringExtra("username");
//                            winIntent.putExtra("score",score);
//                            winIntent.putExtra("username",uname);
//                            startActivity(winIntent);
//                            finish();
//                        }
//
//                    }
//                }
//                public void onSwipeRight(){
//
//
//                    bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
//                    exchangeTag = tagInt+1;
//                    if(tag.equals("1")||tag.equals("4") ||tag.equals("7") ){
//                        if(exchangeTag==2){
//                            bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
//                            imageViews[1].setImageBitmap(bm);
//                            imageViews[0].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==5){
//                            bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
//                            imageViews[4].setImageBitmap(bm);
//                            imageViews[3].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==8){
//                            bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
//                            imageViews[7].setImageBitmap(bm);
//                            imageViews[6].setImageBitmap(bmNew);
//                        }
//                    }
//                    else if(tag.equals("2") ||tag.equals("5") ||tag.equals("8") ){
//                        if(exchangeTag==3){
//                            bmNew = ((BitmapDrawable)imageViews[2].getDrawable()).getBitmap();
//                            imageViews[2].setImageBitmap(bm);
//                            imageViews[1].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==6){
//                            bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
//                            imageViews[5].setImageBitmap(bm);
//                            imageViews[4].setImageBitmap(bmNew);
//                        }
//                        else if(exchangeTag==9){
//                            bmNew = ((BitmapDrawable)imageViews[8].getDrawable()).getBitmap();
//                            imageViews[8].setImageBitmap(bm);
//                            imageViews[7].setImageBitmap(bmNew);
//                        }
//
//                    }
//                    int victory = 0;
//                    for(int j=0;j<9;j++){
//                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
//                        if(bms == finalDividedBitmaps.get(j)){
//                            victory++;
//                        }
//                        if(victory==9){
//                            double score = 3*10000/(timeElapsed*0.9*2.85);
//                            String uname = getIntent().getStringExtra("username");
//                            winIntent.putExtra("score",score);
//                            winIntent.putExtra("username",uname);
//                            startActivity(winIntent);
//                            finish();
//                        }
//
//                    }
//                }


//                                   }

//            );


//        }
    }
}