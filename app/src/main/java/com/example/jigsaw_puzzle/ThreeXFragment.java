package com.example.jigsaw_puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        img1.setOnTouchListener(new OnSwipeTouchListener(context){
            public void onSwipeTop() {
                Log.i("name","top");
            }
            public void onSwipeRight() {
                Log.i("name","right");
            }
            public void onSwipeLeft() {
                Log.i("name","left");
            }
            public void onSwipeBottom() {
                Log.i("name","bottom");
            }
        });
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