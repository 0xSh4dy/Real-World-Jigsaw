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
 * Use the {@link FivexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ClickableViewAccessibility")

public class FivexFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context ctx;
    private ImageView[]imageViewsAlt;
    private int timeElapsed=0;
    private int n_moves=0;
    private TextView movesTV;
    private Bitmap btm;
    public FivexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FivexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FivexFragment newInstance(String param1, String param2) {
        FivexFragment fragment = new FivexFragment();
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
        return inflater.inflate(R.layout.fragment_fivex, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movesTV = requireActivity().findViewById(R.id.movesTV2);
        assert getArguments() != null;
        Intent winIntent = new Intent(ctx,WinningActivity.class);
        byte[] byteArray = getArguments().getByteArray("image");
        ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22,i23,i24,i25;
        ArrayList<Bitmap> dividedBitmaps;
        i1 = requireView().findViewById(R.id.i51);
        i2 = requireView().findViewById(R.id.i52);
        i3 = requireView().findViewById(R.id.i53);
        i4 = requireView().findViewById(R.id.i54);
        i5 = requireView().findViewById(R.id.i55);
        i6 = requireView().findViewById(R.id.i56);
        i7 = requireView().findViewById(R.id.i57);
        i8 = requireView().findViewById(R.id.i58);
        i9 = requireView().findViewById(R.id.i59);
        i10 = requireView().findViewById(R.id.i60);
        i11 = requireView().findViewById(R.id.i61);
        i12 = requireView().findViewById(R.id.i62);
        i13 = requireView().findViewById(R.id.i63);
        i14 = requireView().findViewById(R.id.i64);
        i15 = requireView().findViewById(R.id.i65);
        i16 = requireView().findViewById(R.id.i66);
        i17 = requireView().findViewById(R.id.i67);
        i18 = requireView().findViewById(R.id.i68);
        i19 = requireView().findViewById(R.id.i69);
        i20 = requireView().findViewById(R.id.i70);
        i21 = requireView().findViewById(R.id.i71);
        i22 = requireView().findViewById(R.id.i72);
        i23 = requireView().findViewById(R.id.i73);
        i24 = requireView().findViewById(R.id.i74);
        i25 = requireView().findViewById(R.id.i75);
        ImageView[] imageViews1 = new ImageView[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16,i17,i18,i19,i20,i21,i22,i23,i24,i25};
        imageViewsAlt = imageViews1;
        Bitmap imageCapture = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        final int imageWidth = imageCapture.getWidth();
        final int imageHeight = imageCapture.getHeight();
        ImpFunctions imf = new ImpFunctions();
        SharedPreferences preferences = this.ctx.getSharedPreferences("auth",Context.MODE_PRIVATE);
        String uname = preferences.getString("username","");
        dividedBitmaps = imf.splitBitmap(imageCapture,5,imageHeight/5,imageWidth/5,imageWidth,imageHeight);
        imf.shuffleImages(imageViews1,5,dividedBitmaps);
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
                    if(tagInt>=6 && tagInt<=25){
                        switch(tagInt){
                            case 6:
                                btm = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                i1.setImageBitmap(bm2);
                                i6.setImageBitmap(btm);
                                break;
                            case 7:
                                btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                i2.setImageBitmap(bm2);
                                i7.setImageBitmap(btm);
                                break;
                            case 8:
                                btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                i3.setImageBitmap(bm2);
                                i8.setImageBitmap(btm);
                                break;
                            case 9:
                                btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                i4.setImageBitmap(bm2);
                                i9.setImageBitmap(btm);
                                break;
                            case 10:
                                btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                i5.setImageBitmap(bm2);
                                i10.setImageBitmap(btm);
                                break;
                            case 11:
                                btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                i6.setImageBitmap(bm2);
                                i11.setImageBitmap(btm);
                                break;
                            case 12:
                                btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                i7.setImageBitmap(bm2);
                                i12.setImageBitmap(btm);
                                break;
                            case 13:
                                btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                i8.setImageBitmap(bm2);
                                i13.setImageBitmap(btm);
                                break;
                            case 14:
                                btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                i9.setImageBitmap(bm2);
                                i14.setImageBitmap(btm);
                                break;
                            case 15:
                                btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                i10.setImageBitmap(bm2);
                                i15.setImageBitmap(btm);
                                break;
                            case 16:
                                btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                i11.setImageBitmap(bm2);
                                i16.setImageBitmap(btm);
                                break;
                            case 17:
                                btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                i12.setImageBitmap(bm2);
                                i17.setImageBitmap(btm);
                                break;
                            case 18:
                                btm = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                i13.setImageBitmap(bm2);
                                i18.setImageBitmap(btm);
                                break;
                            case 19:
                                btm = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                i14.setImageBitmap(bm2);
                                i19.setImageBitmap(btm);
                                break;
                            case 20:
                                btm = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                                i15.setImageBitmap(bm2);
                                i20.setImageBitmap(btm);
                                break;
                            case 21:
                                btm = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i21.getDrawable()).getBitmap();
                                i16.setImageBitmap(bm2);
                                i21.setImageBitmap(btm);
                                break;
                            case 22:
                                btm = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                                i17.setImageBitmap(bm2);
                                i22.setImageBitmap(btm);
                                break;
                            case 23:
                                btm = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                                i18.setImageBitmap(bm2);
                                i23.setImageBitmap(btm);
                                break;
                            case 24:
                                btm = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                                i19.setImageBitmap(bm2);
                                i24.setImageBitmap(btm);
                                break;
                            case 25:
                                btm = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i25.getDrawable()).getBitmap();
                                i20.setImageBitmap(bm2);
                                i25.setImageBitmap(btm);
                                break;
                        }
                        n_moves++;
                        String txt = "Moves: "+n_moves;
                        movesTV.setText(txt);
                        int victory = 0;
                        for(int j=0;j<25;j++){
                            Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                            if(bms == dividedBitmaps.get(j)){
                                victory++;
                            }
                            if(victory==25){
                                double score = 10*10000/((timeElapsed+n_moves) *2.5);
                                winIntent.putExtra("score",score);
                                winIntent.putExtra("username",uname);
                                winIntent.putExtra("mode","5x5");
                                startActivity(winIntent);
                                requireActivity().finish();
                            }

                        }
                    }
                }

                @Override
                public void onSwipeBottom() {
                    super.onSwipeBottom();
                    if(tagInt>=1 && tagInt<=20){
                        switch(tagInt){
                            case 1:
                                btm = ((BitmapDrawable)i1.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                i1.setImageBitmap(bm2);
                                i6.setImageBitmap(btm);
                                break;
                            case 2:
                                btm = ((BitmapDrawable)i2.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                i2.setImageBitmap(bm2);
                                i7.setImageBitmap(btm);
                                break;
                            case 3:
                                btm = ((BitmapDrawable)i3.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                i3.setImageBitmap(bm2);
                                i8.setImageBitmap(btm);
                                break;
                            case 4:
                                btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                i4.setImageBitmap(bm2);
                                i9.setImageBitmap(btm);
                                break;
                            case 5:
                                btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                i5.setImageBitmap(bm2);
                                i10.setImageBitmap(btm);
                                break;
                            case 6:
                                btm = ((BitmapDrawable)i6.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                i6.setImageBitmap(bm2);
                                i11.setImageBitmap(btm);
                                break;
                            case 7:
                                btm = ((BitmapDrawable)i7.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                i7.setImageBitmap(bm2);
                                i12.setImageBitmap(btm);
                                break;
                            case 8:
                                btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                i8.setImageBitmap(bm2);
                                i13.setImageBitmap(btm);
                                break;
                            case 9:
                                btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                i9.setImageBitmap(bm2);
                                i14.setImageBitmap(btm);
                                break;
                            case 10:
                                btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                i10.setImageBitmap(bm2);
                                i15.setImageBitmap(btm);
                                break;
                            case 11:
                                btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                i11.setImageBitmap(bm2);
                                i16.setImageBitmap(btm);
                                break;
                            case 12:
                                btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                i12.setImageBitmap(bm2);
                                i17.setImageBitmap(btm);
                                break;
                            case 13:
                                btm = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                i13.setImageBitmap(bm2);
                                i18.setImageBitmap(btm);
                                break;
                            case 14:
                                btm = ((BitmapDrawable)i14.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                i14.setImageBitmap(bm2);
                                i19.setImageBitmap(btm);
                                break;
                            case 15:
                                btm = ((BitmapDrawable)i15.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                                i15.setImageBitmap(bm2);
                                i20.setImageBitmap(btm);
                                break;
                            case 16:
                                btm = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i21.getDrawable()).getBitmap();
                                i16.setImageBitmap(bm2);
                                i21.setImageBitmap(btm);
                                break;
                            case 17:
                                btm = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                                i17.setImageBitmap(bm2);
                                i22.setImageBitmap(btm);
                                break;
                            case 18:
                                btm = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                                i18.setImageBitmap(bm2);
                                i23.setImageBitmap(btm);
                                break;
                            case 19:
                                btm = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                                i19.setImageBitmap(bm2);
                                i24.setImageBitmap(btm);
                                break;
                            case 20:
                                btm = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i25.getDrawable()).getBitmap();
                                i20.setImageBitmap(bm2);
                                i25.setImageBitmap(btm);
                                break;
                        }
                        n_moves++;
                        String txt = "Moves: "+n_moves;
                        movesTV.setText(txt);
                        int victory = 0;
                        for(int j=0;j<25;j++){
                            Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                            if(bms == dividedBitmaps.get(j)){
                                victory++;
                            }
                            if(victory==25){
                                double score = 10*10000/((timeElapsed+n_moves) *2.5);
                                winIntent.putExtra("score",score);
                                winIntent.putExtra("username",uname);
                                winIntent.putExtra("mode","5x5");
                                startActivity(winIntent);
                                requireActivity().finish();
                            }

                        }
                    }
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    if(tagInt>=1&&tagInt<=4 || tagInt>=6&&tagInt<=9||tagInt>=11&&tagInt<=14||tagInt>=16&&tagInt<=19 || tagInt>=21&&tagInt<=24){
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
                            case 4:
                                btm = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                                i4.setImageBitmap(bm2);
                                i5.setImageBitmap(btm);
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
                            case 8:
                                btm = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                i8.setImageBitmap(bm2);
                                i9.setImageBitmap(btm);
                                break;
                            case 9:
                                btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                                i9.setImageBitmap(bm2);
                                i10.setImageBitmap(btm);
                                break;

                            case 11:
                                btm = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                i11.setImageBitmap(bm2);
                                i12.setImageBitmap(btm);
                                break;
                            case 12:
                                btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                                i12.setImageBitmap(bm2);
                                i13.setImageBitmap(btm);
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
                            case 16:
                                btm = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                i16.setImageBitmap(bm2);
                                i17.setImageBitmap(btm);
                                break;
                            case 17:
                                btm = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                i17.setImageBitmap(bm2);
                                i18.setImageBitmap(btm);
                                break;
                            case 18:
                                btm = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                i18.setImageBitmap(bm2);
                                i19.setImageBitmap(btm);
                                break;
                            case 19:
                                btm = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                                i19.setImageBitmap(bm2);
                                i20.setImageBitmap(btm);
                                break;
                            case 21:
                                btm = ((BitmapDrawable)i21.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                                i21.setImageBitmap(bm2);
                                i22.setImageBitmap(btm);
                                break;
                            case 22:
                                btm = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                                i22.setImageBitmap(bm2);
                                i23.setImageBitmap(btm);
                                break;
                            case 23:
                                btm = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                                i23.setImageBitmap(bm2);
                                i24.setImageBitmap(btm);
                                break;
                            case 24:
                                btm = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                                bm2 = ((BitmapDrawable)i25.getDrawable()).getBitmap();
                                i24.setImageBitmap(bm2);
                                i25.setImageBitmap(btm);
                                break;
                        }
                        n_moves++;
                        String txt = "Moves: "+n_moves;
                        movesTV.setText(txt);
                        int victory = 0;
                        for(int j=0;j<25;j++){
                            Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                            if(bms == dividedBitmaps.get(j)){
                                victory++;
                            }
                            if(victory==25){
                                double score = 10*10000/((timeElapsed+n_moves) *2.5);
                                winIntent.putExtra("score",score);
                                winIntent.putExtra("username",uname);
                                winIntent.putExtra("mode","5x5");
                                startActivity(winIntent);
                                requireActivity().finish();
                            }

                        }
                    }
                }

                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    if(tagInt>=2&&tagInt<=5||tagInt>=7&&tagInt<=10||tagInt>=12&&tagInt<=15||tagInt>=17&&tagInt<=20||tagInt>=22&&tagInt<=25){
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
                        case 5:
                            btm = ((BitmapDrawable)i5.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i4.getDrawable()).getBitmap();
                            i5.setImageBitmap(bm2);
                            i4.setImageBitmap(btm);
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
                        case 9:
                            btm = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i8.getDrawable()).getBitmap();
                            i9.setImageBitmap(bm2);
                            i8.setImageBitmap(btm);
                            break;
                        case 10:
                            btm = ((BitmapDrawable)i10.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i9.getDrawable()).getBitmap();
                            i10.setImageBitmap(bm2);
                            i9.setImageBitmap(btm);
                            break;
                        case 12:
                            btm = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i11.getDrawable()).getBitmap();
                            i12.setImageBitmap(bm2);
                            i11.setImageBitmap(btm);
                            break;
                        case 13:
                            btm = ((BitmapDrawable)i13.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i12.getDrawable()).getBitmap();
                            i13.setImageBitmap(bm2);
                            i12.setImageBitmap(btm);
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
                        case 17:
                            btm = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i16.getDrawable()).getBitmap();
                            i17.setImageBitmap(bm2);
                            i16.setImageBitmap(btm);
                            break;
                        case 18:
                            btm = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i17.getDrawable()).getBitmap();
                            i18.setImageBitmap(bm2);
                            i17.setImageBitmap(btm);
                            break;
                        case 19:
                            btm = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i18.getDrawable()).getBitmap();
                            i19.setImageBitmap(bm2);
                            i18.setImageBitmap(btm);
                            break;
                        case 20:
                            btm = ((BitmapDrawable)i20.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i19.getDrawable()).getBitmap();
                            i20.setImageBitmap(bm2);
                            i19.setImageBitmap(btm);
                            break;
                        case 22:
                            btm = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i21.getDrawable()).getBitmap();
                            i22.setImageBitmap(bm2);
                            i21.setImageBitmap(btm);
                            break;
                        case 23:
                            btm = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i22.getDrawable()).getBitmap();
                            i23.setImageBitmap(bm2);
                            i22.setImageBitmap(btm);
                            break;
                        case 24:
                            btm = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i23.getDrawable()).getBitmap();
                            i24.setImageBitmap(bm2);
                            i23.setImageBitmap(btm);
                            break;
                        case 25:
                            btm = ((BitmapDrawable)i25.getDrawable()).getBitmap();
                            bm2 = ((BitmapDrawable)i24.getDrawable()).getBitmap();
                            i25.setImageBitmap(bm2);
                            i24.setImageBitmap(btm);
                            break;

                    }
                    n_moves++;
                    String txt = "Moves: "+n_moves;
                    movesTV.setText(txt);
                    int victory = 0;
                    for(int j=0;j<25;j++){
                        Bitmap bms = ((BitmapDrawable)imageViewsAlt[j].getDrawable()).getBitmap();
                        if(bms == dividedBitmaps.get(j)){
                            victory++;
                        }
                        if(victory==25){
                            double score = 10*10000/((timeElapsed+n_moves) *2.5);
                            winIntent.putExtra("score",score);
                            winIntent.putExtra("username",uname);
                            winIntent.putExtra("mode","5x5");
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