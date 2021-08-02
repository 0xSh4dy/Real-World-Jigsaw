package com.example.jigsaw_puzzle;

import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ImpFunctions {
    public ArrayList<Bitmap> splitBitmap(Bitmap bitmap, int gridSize, int targetImageHeight, int targetImageWidth, int imageWidth, int imageHeight ){
        ArrayList<Bitmap> smallBitmaps = new ArrayList<Bitmap>();
        int x = 0;
        int y=0;
        if(gridSize==3){
            for(int i=0;i<gridSize*gridSize;i++){
                Bitmap modifiedBitmap = Bitmap.createBitmap(bitmap,x,y,targetImageWidth,targetImageHeight);
                if(i==2 || i==5){
                    x=0;
                    y+=imageHeight/3;
                }
                else{
                    x+=imageWidth/3;
                }
                smallBitmaps.add(modifiedBitmap);
            }
        }
//        else if(gridSize==4){
//
//        }
//        else if(gridSize==5){
//
//        }
        return smallBitmaps;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int[] shuffleImages(ImageView []imgs, ArrayList<Bitmap> bitmaps){
        int pointer = 0;
        int []visited = {0,0,0,0,0,0,0,0,0};
        int []finalPositions = {0,0,0,0,0,0,0,0,0};
        int len = imgs.length;
        int low = 0;
        int high = 8;
        while(true){
            int validate=0;
            for(int i=0;i<=8;i++){
                validate += visited[i];
            }
            if(validate!=9){
            int randomNum = ThreadLocalRandom.current().nextInt(low, high + 1);
            if(visited[randomNum]==0){
                visited[randomNum] =1;
                imgs[randomNum].setImageBitmap(bitmaps.get(pointer));
                finalPositions[pointer] = randomNum;
                pointer++;
            }

            }
            else{
                break;
            }
        }
        return finalPositions;

    }


}
