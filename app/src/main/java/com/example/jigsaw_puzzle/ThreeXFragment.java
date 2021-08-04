package com.example.jigsaw_puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ClickableViewAccessibility")

public class ThreeXFragment extends Fragment {
        Context context;
        int timeElapsed;
        int n_moves=0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ThreeXFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreeXFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeXFragment newInstance(String param1, String param2) {
        ThreeXFragment fragment = new ThreeXFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three_x, container, false);

    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent winIntent = new Intent(getActivity(),WinningActivity.class);
        ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;

        Chronometer chronometer = getActivity().findViewById(R.id.simpleChronometer);
        int score;
        timeElapsed=0;
        ArrayList<Bitmap> dividedBitmaps = null;
        assert getArguments() != null;
        img1 = (ImageView) getView().findViewById(R.id.imageView1);
        img2 = (ImageView) getView().findViewById(R.id.imageView2);
        img3 = (ImageView) getView().findViewById(R.id.imageView3);
        img4 = (ImageView) getView().findViewById(R.id.imageView4);
        img5 = (ImageView) getView().findViewById(R.id.imageView5);
        img6 = (ImageView) getView().findViewById(R.id.imageView6);
        img7 = (ImageView) getView().findViewById(R.id.imageView7);
        img8 = (ImageView) getView().findViewById(R.id.imageView8);
        img9 = (ImageView) getView().findViewById(R.id.imageView9);
        ImageView [] imageViews = {img1,img2,img3,img4,img5,img6,img7,img8,img9};
        ImageView[] imageViewsAlt = imageViews;

        byte[] byteArray = getArguments().getByteArray("image");
        Bitmap imageCapture = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        final int imageHeight = imageCapture.getHeight();
        final int imageWidth = imageCapture.getWidth();
        final int targetImageHeight = img1.getHeight();
        final int targetImageWidth = img1.getWidth();
        boolean won;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeElapsed++;
            }
        },0,1000);

        ImpFunctions impFunctions = new ImpFunctions();
//        img1.setImageBitmap(bitmap);
        dividedBitmaps = impFunctions.splitBitmap(imageCapture,3,imageHeight/3,imageWidth/3,imageWidth,imageHeight);
        impFunctions.shuffleImages(imageViews,3,dividedBitmaps);
        chronometer.start();
        for(ImageView img:imageViews){

            ArrayList<Bitmap> finalDividedBitmaps = dividedBitmaps;
            img.setOnTouchListener(new OnSwipeTouchListener(context){

                                       String tag = img.getTag().toString() ;
                                       int tagInt = Integer.parseInt(tag) ;
                                       Bitmap bmNew;
                                       int exchangeTag;
                                       Bitmap bm;

                                       public void  onSwipeTop(){

                                           exchangeTag = tagInt-3;
                                           bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
                                           if(tag.equals("4") || tag.equals("5") || tag.equals("6")){

                                               if(exchangeTag==1){
                                                   bmNew = ((BitmapDrawable)imageViews[0].getDrawable()).getBitmap();
                                                   imageViews[0].setImageBitmap(bm);
                                                   imageViews[3].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==2){
                                                   bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
                                                   imageViews[1].setImageBitmap(bm);
                                                   imageViews[4].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==3){
                                                   bmNew = ((BitmapDrawable)imageViews[2].getDrawable()).getBitmap();
                                                   imageViews[2].setImageBitmap(bm);
                                                   imageViews[5].setImageBitmap(bmNew);
                                               }

                                           }
                                           else if(tag.equals("7") || tag.equals("8") || tag.equals("9")){
                                               if(exchangeTag==4){
                                                   bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
                                                   imageViews[6].setImageBitmap(bmNew);
                                                   imageViews[3].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==5){
                                                   bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
                                                   imageViews[7].setImageBitmap(bmNew);
                                                   imageViews[4].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==6){
                                                   bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
                                                   imageViews[8].setImageBitmap(bmNew);
                                                   imageViews[5].setImageBitmap(bm);
                                               }
                                           }
                                           n_moves++;
                                           int victory = 0;
                                           for(int j=0;j<9;j++){
                                               Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                               if(bms == finalDividedBitmaps.get(j)){
                                                   victory++;
                                               }
                                               if(victory==9){
                                                   double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                   String uname = getActivity().getIntent().getStringExtra("username");
                                                   winIntent.putExtra("score",score);
                                                   winIntent.putExtra("username",uname);
                                                   startActivity(winIntent);
                                                   getActivity().finish();
                                               }

                                           }
                                       }
                                       public void onSwipeBottom(){


                                           bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
                                           exchangeTag = tagInt+3;
                                           if(tag.equals("1") || tag.equals("2") ||tag.equals("3")){
                                               if(exchangeTag==4){
                                                   bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
                                                   imageViews[0].setImageBitmap(bmNew);
                                                   imageViews[3].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==5){
                                                   bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
                                                   imageViews[1].setImageBitmap(bmNew);
                                                   imageViews[4].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==6){
                                                   bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
                                                   imageViews[2].setImageBitmap(bmNew);
                                                   imageViews[5].setImageBitmap(bm);
                                               }
                                           }
                                           else if(tag.equals("4")|| tag.equals("5")|| tag.equals("6")){
                                               if(exchangeTag==7){
                                                   bmNew = ((BitmapDrawable)imageViews[6].getDrawable()).getBitmap();
                                                   imageViews[3].setImageBitmap(bmNew);
                                                   imageViews[6].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==8){
                                                   bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
                                                   imageViews[4].setImageBitmap(bmNew);
                                                   imageViews[7].setImageBitmap(bm);
                                               }
                                               else if(exchangeTag==9){
                                                   bmNew = ((BitmapDrawable)imageViews[8].getDrawable()).getBitmap();
                                                   imageViews[5].setImageBitmap(bmNew);
                                                   imageViews[8].setImageBitmap(bm);
                                               }
                                           }
                                           n_moves++;
                                           int victory = 0;
                                           for(int j=0;j<9;j++){
                                               Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                               if(bms == finalDividedBitmaps.get(j)){
                                                   victory++;

                                               }
                                               if(victory==9) {
                                                   double score = 3*10000/((timeElapsed+n_moves) *2.5);

                                                   String uname = getActivity().getIntent().getStringExtra("username");
                                                   winIntent.putExtra("score",score);
                                                   winIntent.putExtra("username",uname);
                                                   startActivity(winIntent);
                                                   getActivity().finish();
                                               }
                                           }
                                       }
                                       public void onSwipeLeft(){


                                           bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
                                           exchangeTag = tagInt-1;
                                           if(tag.equals("2")||tag.equals("5")||tag.equals("8")){
                                               if(exchangeTag==1){
                                                   bmNew = ((BitmapDrawable)imageViews[0].getDrawable()).getBitmap();
                                                   imageViews[0].setImageBitmap(bm);
                                                   imageViews[1].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==4){
                                                   bmNew = ((BitmapDrawable)imageViews[3].getDrawable()).getBitmap();
                                                   imageViews[3].setImageBitmap(bm);
                                                   imageViews[4].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==7){
                                                   bmNew = ((BitmapDrawable)imageViews[6].getDrawable()).getBitmap();
                                                   imageViews[6].setImageBitmap(bm);
                                                   imageViews[7].setImageBitmap(bmNew);
                                               }
                                           }
                                           else if(tag.equals("3")||tag.equals("6")||tag.equals("9")){
                                               if(exchangeTag==2){
                                                   bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
                                                   imageViews[1].setImageBitmap(bm);
                                                   imageViews[2].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==5){
                                                   bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
                                                   imageViews[4].setImageBitmap(bm);
                                                   imageViews[5].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==8){
                                                   bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
                                                   imageViews[7].setImageBitmap(bm);
                                                   imageViews[8].setImageBitmap(bmNew);
                                               }
                                           }
                                           n_moves++;
                                           int victory = 0;
                                           for(int j=0;j<9;j++){
                                               Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                               if(bms == finalDividedBitmaps.get(j)){
                                                   victory++;
                                               }
                                               if(victory==9){
                                                   double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                   String uname = getActivity().getIntent().getStringExtra("username");
                                                   winIntent.putExtra("score",score);
                                                   winIntent.putExtra("username",uname);
                                                   startActivity(winIntent);
                                                   getActivity().finish();
                                               }

                                           }
                                       }
                                       public void onSwipeRight(){


                                           bm= ((BitmapDrawable)img.getDrawable()).getBitmap();
                                           exchangeTag = tagInt+1;
                                           if(tag.equals("1")||tag.equals("4") ||tag.equals("7") ){
                                               if(exchangeTag==2){
                                                   bmNew = ((BitmapDrawable)imageViews[1].getDrawable()).getBitmap();
                                                   imageViews[1].setImageBitmap(bm);
                                                   imageViews[0].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==5){
                                                   bmNew = ((BitmapDrawable)imageViews[4].getDrawable()).getBitmap();
                                                   imageViews[4].setImageBitmap(bm);
                                                   imageViews[3].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==8){
                                                   bmNew = ((BitmapDrawable)imageViews[7].getDrawable()).getBitmap();
                                                   imageViews[7].setImageBitmap(bm);
                                                   imageViews[6].setImageBitmap(bmNew);
                                               }
                                           }
                                           else if(tag.equals("2") ||tag.equals("5") ||tag.equals("8") ){
                                               if(exchangeTag==3){
                                                   bmNew = ((BitmapDrawable)imageViews[2].getDrawable()).getBitmap();
                                                   imageViews[2].setImageBitmap(bm);
                                                   imageViews[1].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==6){
                                                   bmNew = ((BitmapDrawable)imageViews[5].getDrawable()).getBitmap();
                                                   imageViews[5].setImageBitmap(bm);
                                                   imageViews[4].setImageBitmap(bmNew);
                                               }
                                               else if(exchangeTag==9){
                                                   bmNew = ((BitmapDrawable)imageViews[8].getDrawable()).getBitmap();
                                                   imageViews[8].setImageBitmap(bm);
                                                   imageViews[7].setImageBitmap(bmNew);
                                               }

                                           }
                                           n_moves++;
                                           int victory = 0;
                                           for(int j=0;j<9;j++){
                                               Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                                               if(bms == finalDividedBitmaps.get(j)){
                                                   victory++;
                                               }
                                               if(victory==9){
                                                   double score = 3*10000/((timeElapsed+n_moves) *2.5);
                                                   String uname = getActivity().getIntent().getStringExtra("username");
                                                   winIntent.putExtra("score",score);
                                                   winIntent.putExtra("username",uname);
                                                   startActivity(winIntent);
                                                   getActivity().finish();
                                               }

                                           }
                                       }


                                   }

            );


        }

    }

}