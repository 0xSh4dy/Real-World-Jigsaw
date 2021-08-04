package com.example.jigsaw_puzzle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourxFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context ctx;
    private ImageView[]imageViewsAlt;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        Intent winIntent = new Intent(getActivity(),WinningActivity.class);
        ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16;
        ArrayList<Bitmap>dividedBitmaps = new ArrayList<>();
        i1 = (ImageView)getView().findViewById(R.id.i1);
        i2 = (ImageView)getView().findViewById(R.id.i2);
        i3 = (ImageView)getView().findViewById(R.id.i3);
        i4 = (ImageView)getView().findViewById(R.id.i4);
        i5 = (ImageView)getView().findViewById(R.id.i5);
        i6 = (ImageView)getView().findViewById(R.id.i6);
        i7 = (ImageView)getView().findViewById(R.id.i7);
        i8 = (ImageView)getView().findViewById(R.id.i8);
        i9 = (ImageView)getView().findViewById(R.id.i9);
        i10 = (ImageView)getView().findViewById(R.id.i10);
        i11 = (ImageView)getView().findViewById(R.id.i11);
        i12 = (ImageView)getView().findViewById(R.id.i12);
        i13 = (ImageView)getView().findViewById(R.id.i13);
        i14 = (ImageView)getView().findViewById(R.id.i14);
        i15 = (ImageView)getView().findViewById(R.id.i15);
        i16 = (ImageView)getView().findViewById(R.id.i16);
        ImageView[] imageViews = new ImageView[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16};
        imageViewsAlt = imageViews;
        byte[] byteArray = getArguments().getByteArray("image");
        Bitmap imageCapture = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        final int imageHeight = imageCapture.getHeight();
        final int imageWidth = imageCapture.getWidth();
        ImpFunctions imF = new ImpFunctions();
        dividedBitmaps = imF.splitBitmap(imageCapture,4,imageHeight/4,imageWidth/4,imageWidth,imageHeight);
        int[]modifiedPositions =  imF.shuffleImages(imageViews,4,dividedBitmaps);

    }
}