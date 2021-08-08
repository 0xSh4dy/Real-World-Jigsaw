package com.example.jigsaw_puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ClickableViewAccessibility")
public class FourxFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context ctx;
    private ImageView[]imageViewsAlt;
    private Bitmap btm;
    private int timeElapsed=0;
    private int n_moves = 0;
    private TextView movesTV;
    public FourxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourxFragment newInstance(String param1, String param2) {
        FourxFragment fragment = new FourxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourx, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movesTV = requireActivity().findViewById(R.id.movesTV2);
        Intent winIntent = new Intent(getActivity(),WinningActivity.class);
        ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16;
        ArrayList<Bitmap>dividedBitmaps;
        i1 = requireView().findViewById(R.id.i1);
        i2 = requireView().findViewById(R.id.i2);
        i3 = requireView().findViewById(R.id.i3);
        i4 = requireView().findViewById(R.id.i4);
        i5 = requireView().findViewById(R.id.i5);
        i6 = requireView().findViewById(R.id.i6);
        i7 = requireView().findViewById(R.id.i7);
        i8 = requireView().findViewById(R.id.i8);
        i9 = requireView().findViewById(R.id.i9);
        i10 = requireView().findViewById(R.id.i10);
        i11 = requireView().findViewById(R.id.i11);
        i12 = requireView().findViewById(R.id.i12);
        i13 = requireView().findViewById(R.id.i13);
        i14 = requireView().findViewById(R.id.i14);
        i15 = requireView().findViewById(R.id.i15);
        i16 = requireView().findViewById(R.id.i16);
        ImageView[] imageViews1 = new ImageView[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16};
        imageViewsAlt = imageViews1;
        SharedPreferences preferences = this.ctx.getSharedPreferences("auth",Context.MODE_PRIVATE);
        String uname = preferences.getString("username","");
        assert getArguments() != null;
        byte[] byteArray = getArguments().getByteArray("image");
        Bitmap imageCapture = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        final int imageHeight = imageCapture.getHeight();
        final int imageWidth = imageCapture.getWidth();
        ImpFunctions imF = new ImpFunctions();
        dividedBitmaps = imF.splitBitmap(imageCapture,4,imageHeight/4,imageWidth/4,imageWidth,imageHeight);
        imF.shuffleImages(imageViews1,4,dividedBitmaps);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeElapsed++;
            }
        },0,1000);
        Chronometer chronometer = requireActivity().findViewById(R.id.simpleChronometer);
        chronometer.setVisibility(View.VISIBLE);
        movesTV.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.start();
        for(ImageView i:imageViews1){
            i.setOnTouchListener(new OnSwipeTouchListener(ctx){
                final String tag = i.getTag().toString() ;
                final int tagInt = Integer.parseInt(tag) ;
                Bitmap bm2;

                @Override
                public void onSwipeTop() {
                    super.onSwipeTop();
                    if(tagInt>=5 && tagInt<=16){
                        switch(tagInt){
                            case 5:
                                btm = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                i1.setImageBitmap(bm2);
                                i5.setImageBitmap(btm);
                                break;
                            case 6:
                                btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                i2.setImageBitmap(bm2);
                                i6.setImageBitmap(btm);
                                break;
                            case 7:
                                btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                i3.setImageBitmap(bm2);
                                i7.setImageBitmap(btm);
                                break;
                            case 8:
                                btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                i4.setImageBitmap(bm2);
                                i8.setImageBitmap(btm);
                                break;
                            case 9:
                                btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                i5.setImageBitmap(bm2);
                                i9.setImageBitmap(btm);
                                break;
                            case 10:
                                btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                i6.setImageBitmap(bm2);
                                i10.setImageBitmap(btm);
                                break;
                            case 11:
                                btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                i7.setImageBitmap(bm2);
                                i11.setImageBitmap(btm);
                                break;
                            case 12:
                                btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                i8.setImageBitmap(bm2);
                                i12.setImageBitmap(btm);
                                break;
                            case 13:
                                btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                i9.setImageBitmap(bm2);
                                i13.setImageBitmap(btm);
                                break;
                            case 14:
                                btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                i10.setImageBitmap(bm2);
                                i14.setImageBitmap(btm);
                                break;
                            case 15:
                                btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                i11.setImageBitmap(bm2);
                                i15.setImageBitmap(btm);
                                break;
                            case 16:
                                btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                i12.setImageBitmap(bm2);
                                i16.setImageBitmap(btm);
                                break;
                        }
                        n_moves++;
                        String txt = "Moves: "+n_moves;
                        movesTV.setText(txt);
                        int victory = 0;
                        for(int j=0;j<16;j++){
                            Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                            if(bms == dividedBitmaps.get(j)){
                                victory++;
                            }
                            if(victory==16){
                                double score = 5*10000/((timeElapsed+n_moves) *2.5);
                                winIntent.putExtra("score",score);
                                winIntent.putExtra("username",uname);
                                winIntent.putExtra("mode","4x4");
                                startActivity(winIntent);
                                requireActivity().finish();
                            }

                        }
                    }
                }

                @Override
                public void onSwipeBottom() {
                    super.onSwipeBottom();
                    if(tagInt>=1 && tagInt<=12){
                        switch(tagInt){
                            case 1:
                                btm = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                i1.setImageBitmap(bm2);
                                i5.setImageBitmap(btm);
                                break;
                            case 2:
                                btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                i2.setImageBitmap(bm2);
                                i6.setImageBitmap(btm);
                                break;
                            case 3:
                                btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                i3.setImageBitmap(bm2);
                                i7.setImageBitmap(btm);
                                break;
                            case 4:
                                btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                i4.setImageBitmap(bm2);
                                i8.setImageBitmap(btm);
                                break;
                            case 5:
                                btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                i5.setImageBitmap(bm2);
                                i9.setImageBitmap(btm);
                                break;
                            case 6:
                                btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                i6.setImageBitmap(bm2);
                                i10.setImageBitmap(btm);
                                break;
                            case 7:
                                btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                i7.setImageBitmap(bm2);
                                i11.setImageBitmap(btm);
                                break;
                            case 8:
                                btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                i8.setImageBitmap(bm2);
                                i12.setImageBitmap(btm);
                                break;
                            case 9:
                                btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                i9.setImageBitmap(bm2);
                                i13.setImageBitmap(btm);
                                break;
                            case 10:
                                btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                i10.setImageBitmap(bm2);
                                i14.setImageBitmap(btm);
                                break;
                            case 11:
                                btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                i11.setImageBitmap(bm2);
                                i15.setImageBitmap(btm);
                                break;
                            case 12:
                                btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                i12.setImageBitmap(bm2);
                                i16.setImageBitmap(btm);
                                break;
                        }
                        n_moves++;
                        String txt = "Moves: "+n_moves;
                        movesTV.setText(txt);
                        int victory = 0;
                        for(int j=0;j<16;j++){
                            Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                            if(bms == dividedBitmaps.get(j)){
                                victory++;
                            }
                            if(victory==16){
                                double score = 5*10000/((timeElapsed+n_moves) *2.5);
                                winIntent.putExtra("score",score);
                                winIntent.putExtra("username",uname);
                                winIntent.putExtra("mode","4x4");
                                startActivity(winIntent);
                                requireActivity().finish();
                            }

                        }
                    }

                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    if(tagInt>=1&&tagInt<=3 || tagInt>=5&&tagInt<=7||tagInt>=9&&tagInt<=11||tagInt>=13&&tagInt<=15){
                    switch(tagInt){
                        case 1:
                            btm = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                            i1.setImageBitmap(bm2);
                            i2.setImageBitmap(btm);
                            break;
                        case 2:
                            btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                            i2.setImageBitmap(bm2);
                            i3.setImageBitmap(btm);
                            break;
                        case 3:
                            btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                            i3.setImageBitmap(bm2);
                            i4.setImageBitmap(btm);
                            break;
                        case 5:
                            btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                            i5.setImageBitmap(bm2);
                            i6.setImageBitmap(btm);
                            break;
                        case 6:
                            btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                            i6.setImageBitmap(bm2);
                            i7.setImageBitmap(btm);
                            break;
                        case 7:
                            btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                            i7.setImageBitmap(bm2);
                            i8.setImageBitmap(btm);
                            break;
                        case 9:
                            btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                            i9.setImageBitmap(bm2);
                            i10.setImageBitmap(btm);
                            break;
                        case 10:
                            btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                            i10.setImageBitmap(bm2);
                            i11.setImageBitmap(btm);
                            break;
                        case 11:
                            btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                            i11.setImageBitmap(bm2);
                            i12.setImageBitmap(btm);
                            break;
                        case 13:
                            btm = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                            i13.setImageBitmap(bm2);
                            i14.setImageBitmap(btm);
                            break;
                        case 14:
                            btm = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                            i14.setImageBitmap(bm2);
                            i15.setImageBitmap(btm);
                            break;
                        case 15:
                            btm = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                            i15.setImageBitmap(bm2);
                            i16.setImageBitmap(btm);
                            break;

                    }
                    n_moves++;
                    String txt = "Moves: "+n_moves;
                    movesTV.setText(txt);
                    int victory = 0;
                    for(int j=0;j<16;j++){
                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                        if(bms == dividedBitmaps.get(j)){
                            victory++;
                        }
                        if(victory==16){
                            double score = 5*10000/((timeElapsed+n_moves) *2.5);
                            winIntent.putExtra("score",score);
                            winIntent.putExtra("username",uname);
                            winIntent.putExtra("mode","4x4");
                            startActivity(winIntent);
                            requireActivity().finish();
                        }

                    }
                    }
                }

                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    if(tagInt>=2&&tagInt<=4 || tagInt>=6&&tagInt<=8 || tagInt>=10&&tagInt<=12 ||tagInt>=14&&tagInt<=16){
                    switch(tagInt){
                        case 2:
                            btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                            i2.setImageBitmap(bm2);
                            i1.setImageBitmap(btm);
                            break;
                        case 3:
                            btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                            i3.setImageBitmap(bm2);
                            i2.setImageBitmap(btm);
                            break;
                        case 4:
                            btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                            i4.setImageBitmap(bm2);
                            i3.setImageBitmap(btm);
                            break;
                        case 6:
                            btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                            i6.setImageBitmap(bm2);
                            i5.setImageBitmap(btm);
                            break;
                        case 7:
                            btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                            i7.setImageBitmap(bm2);
                            i6.setImageBitmap(btm);
                            break;
                        case 8:
                            btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                            i8.setImageBitmap(bm2);
                            i7.setImageBitmap(btm);
                            break;
                        case 10:
                            btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                            i10.setImageBitmap(bm2);
                            i9.setImageBitmap(btm);
                            break;
                        case 11:
                            btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                            i11.setImageBitmap(bm2);
                            i10.setImageBitmap(btm);
                            break;
                        case 12:
                            btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                            i12.setImageBitmap(bm2);
                            i11.setImageBitmap(btm);
                            break;
                        case 14:
                            btm = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                            i14.setImageBitmap(bm2);
                            i13.setImageBitmap(btm);
                            break;
                        case 15:
                            btm = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                            i15.setImageBitmap(bm2);
                            i14.setImageBitmap(btm);
                            break;
                        case 16:
                            btm = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                            i16.setImageBitmap(bm2);
                            i15.setImageBitmap(btm);
                            break;
                    }
                    n_moves++;
                    String txt = "Moves: "+n_moves;
                    movesTV.setText(txt);
                    int victory = 0;
                    for(int j=0;j<16;j++){
                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                        if(bms == dividedBitmaps.get(j)){
                            victory++;
                        }
                        if(victory==16){
                            double score = 5*10000/((timeElapsed+n_moves) *2.5);
                            winIntent.putExtra("score",score);
                            winIntent.putExtra("username",uname);
                            winIntent.putExtra("mode","4x4");
                            startActivity(winIntent);
                            requireActivity().finish();
                        }

                    }
                    }
                }
            });
        }
    }
}