package com.example.jigsaw_puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ClickableViewAccessibility")

public class ThreeXFragment extends Fragment {
        Context context;
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
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        ImageView img5;
        ImageView img6;
        ImageView img7;
        ImageView img8;
        ImageView img9;

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
        byte[] byteArray = getArguments().getByteArray("image");
        Bitmap imageCapture = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        final int imageHeight = imageCapture.getHeight();
        final int imageWidth = imageCapture.getWidth();
        final int targetImageHeight = img1.getHeight();
        final int targetImageWidth = img1.getWidth();
//        img1.setOnTouchListener(new OnSwipeTouchListener(context){
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Bitmap bm = ((BitmapDrawable)img2.getDrawable()).getBitmap();
//                Bitmap bm2 = ((BitmapDrawable)img1.getDrawable()).getBitmap();
//                img1.setImageBitmap(bm);
//                img2.setImageBitmap(bm2);
//            }
//        });
        for(ImageView img:imageViews){
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
                                bmNew = ((BitmapDrawable)img1.getDrawable()).getBitmap();
                                img1.setImageBitmap(bm);
                                img4.setImageBitmap(bmNew);
                            }
                            else if(exchangeTag==2){
                                bmNew = ((BitmapDrawable)img2.getDrawable()).getBitmap();
                                img2.setImageBitmap(bm);
                                img5.setImageBitmap(bmNew);
                            }
                            else if(exchangeTag==3){
                                bmNew = ((BitmapDrawable)img3.getDrawable()).getBitmap();
                                img3.setImageBitmap(bm);
                                img6.setImageBitmap(bmNew);
                            }

                        }
                        else if(tag.equals("7") || tag.equals("8") || tag.equals("9")){
                            if(exchangeTag==4){
                                bmNew = ((BitmapDrawable)img4.getDrawable()).getBitmap();
                                img7.setImageBitmap(bmNew);
                                img4.setImageBitmap(bm);
                            }
                            else if(exchangeTag==5){
                                bmNew = ((BitmapDrawable)img5.getDrawable()).getBitmap();
                                img8.setImageBitmap(bmNew);
                                img5.setImageBitmap(bm);
                            }
                            else if(exchangeTag==6){
                                bmNew = ((BitmapDrawable)img6.getDrawable()).getBitmap();
                                img9.setImageBitmap(bmNew);
                                img6.setImageBitmap(bm);
                            }
                        }
                    }
//                    public void onSwipeBottom(){
//                        exchangeTag = tagInt+3;
//                        if(tag.equals("1") || tag.equals("2") ||tag.equals("3")){
//                            if(exchangeTag==4){
//                                bmNew = ((BitmapDrawable)img4.getDrawable()).getBitmap();
//                                img1.setImageBitmap(bmNew);
//                                img4.setImageBitmap(bm);
//                            }
//                            else if(exchangeTag==5){
//                                bmNew = ((BitmapDrawable)img5.getDrawable()).getBitmap();
//                                img2.setImageBitmap(bmNew);
//                                img5.setImageBitmap(bm);
//                            }
//                            else if(exchangeTag==6){
//                                bmNew = ((BitmapDrawable)img6.getDrawable()).getBitmap();
//                                img3.setImageBitmap(bmNew);
//                                img6.setImageBitmap(bm);
//                            }
//                        }
//                        else if(tag.equals("4")|| tag.equals("5")|| tag.equals("6")){
//                            if(exchangeTag==7){
//                                bmNew = ((BitmapDrawable)img7.getDrawable()).getBitmap();
//                                img4.setImageBitmap(bmNew);
//                                img7.setImageBitmap(bm);
//                            }
//                            else if(exchangeTag==8){
//                                bmNew = ((BitmapDrawable)img8.getDrawable()).getBitmap();
//                                img5.setImageBitmap(bmNew);
//                                img8.setImageBitmap(bm);
//                            }
//                            else if(exchangeTag==9){
//                                bmNew = ((BitmapDrawable)img9.getDrawable()).getBitmap();
//                                img6.setImageBitmap(bmNew);
//                                img9.setImageBitmap(bm);
//                            }
//                        }
//                    }
//                    public void onSwipeLeft(){
//                        exchangeTag = tagInt-1;
//                        if(tag.equals("2")||tag.equals("5")||tag.equals("8")){
//                            if(exchangeTag==1){
//                                bmNew = ((BitmapDrawable)img1.getDrawable()).getBitmap();
//                                img1.setImageBitmap(bm);
//                                img2.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==4){
//                                bmNew = ((BitmapDrawable)img4.getDrawable()).getBitmap();
//                                img4.setImageBitmap(bm);
//                                img5.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==7){
//                                bmNew = ((BitmapDrawable)img7.getDrawable()).getBitmap();
//                                img7.setImageBitmap(bm);
//                                img8.setImageBitmap(bmNew);
//                            }
//                        }
//                        else if(tag.equals("3")||tag.equals("6")||tag.equals("9")){
//                            if(exchangeTag==2){
//                                bmNew = ((BitmapDrawable)img2.getDrawable()).getBitmap();
//                                img2.setImageBitmap(bm);
//                                img3.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==5){
//                                bmNew = ((BitmapDrawable)img5.getDrawable()).getBitmap();
//                                img5.setImageBitmap(bm);
//                                img6.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==8){
//                                bmNew = ((BitmapDrawable)img8.getDrawable()).getBitmap();
//                                img8.setImageBitmap(bm);
//                                img9.setImageBitmap(bmNew);
//                            }
//                        }
//                    }
//                    public void onSwipeRight(){
//                        exchangeTag = tagInt+1;
//                        if(tag.equals("1")||tag.equals("4") ||tag.equals("7") ){
//                            if(exchangeTag==2){
//                                bmNew = ((BitmapDrawable)img2.getDrawable()).getBitmap();
//                                img2.setImageBitmap(bm);
//                                img1.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==5){
//                                bmNew = ((BitmapDrawable)img5.getDrawable()).getBitmap();
//                                img5.setImageBitmap(bm);
//                                img4.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==8){
//                                bmNew = ((BitmapDrawable)img8.getDrawable()).getBitmap();
//                                img8.setImageBitmap(bm);
//                                img7.setImageBitmap(bmNew);
//                            }
//                        }
//                        else if(tag.equals("2") ||tag.equals("5") ||tag.equals("8") ){
//                            if(exchangeTag==3){
//                                bmNew = ((BitmapDrawable)img3.getDrawable()).getBitmap();
//                                img3.setImageBitmap(bm);
//                                img2.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==6){
//                                bmNew = ((BitmapDrawable)img6.getDrawable()).getBitmap();
//                                img6.setImageBitmap(bm);
//                                img5.setImageBitmap(bmNew);
//                            }
//                            else if(exchangeTag==9){
//                                bmNew = ((BitmapDrawable)img9.getDrawable()).getBitmap();
//                                img9.setImageBitmap(bm);
//                                img8.setImageBitmap(bmNew);
//                            }
//
//                        }
//                    }

            });
        }

        ImpFunctions impFunctions = new ImpFunctions();
//        img1.setImageBitmap(bitmap);
        ArrayList<Bitmap> dividedBitmaps = impFunctions.splitBitmap(imageCapture,3,imageHeight/3,imageWidth/3,imageWidth,imageHeight);
        int[]modifiedPositions =  impFunctions.shuffleImages(imageViews,dividedBitmaps);
//                for(int i=0;i<9;i++){
//            int low = 0;
//            int high = 8;
//            int randomNum = ThreadLocalRandom.current().nextInt(low, high + 1);
//            int []visited = {0,0,0,0,0,0,0,0,0};
//            imageViews[i].setImageBitmap(dividedBitmaps.get(randomNum));
//        }
    }

}