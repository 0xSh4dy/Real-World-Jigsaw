package com.example.jigsaw_puzzle;

import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ImpFunctions {
    public ArrayList<Bitmap> splitBitmap(Bitmap bitmap, int gridSize, int targetImageHeight, int targetImageWidth, int imageWidth, int imageHeight ){
        ArrayList<Bitmap> smallBitmaps = new ArrayList<>();
        int x;
        int y;
        if(gridSize==3){
            x=0;
            y=0;
            for(int i=0;i<9;i++){
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
        else if(gridSize==4){
            x=0;
            y=0;
            for(int i=0;i<16;i++){
                Bitmap modifiedBitmap = Bitmap.createBitmap(bitmap,x,y,targetImageWidth,targetImageHeight);
                if(i==3 || i==7 || i==11){
                    x=0;
                    y+=imageHeight/4;
                }
                else{
                    x+=imageWidth/4;
                }
                smallBitmaps.add(modifiedBitmap);
            }
        }
        else if(gridSize==5){
            x=0;
            y=0;
            for(int i=0;i<25;i++){
                Bitmap modifiedBitmap = Bitmap.createBitmap(bitmap,x,y,targetImageWidth,targetImageHeight);
                if(i==4||i==9||i==14||i==19){
                    x=0;
                    y+=imageHeight/5;
                }
                else{
                    x+=imageWidth/5;
                }
                smallBitmaps.add(modifiedBitmap);
            }
        }
        return smallBitmaps;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void shuffleImages(ImageView []imgs, int gridSize,ArrayList<Bitmap> bitmaps){
        int pointer;
        int []visited;
        int low,high;
        if(gridSize==3){
            pointer=0;
            low=0;
            high=8;
            visited = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
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
                pointer++;
            }

            }
            else{
                break;
            }
        }
        }
        else if(gridSize==4){
            pointer=0;
            low=0;
            high=15;
            visited = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            while(true){
                int validate=0;
                for(int i=0;i<=15;i++){
                    validate += visited[i];
                }
                if(validate!=16){
                    int randomNum = ThreadLocalRandom.current().nextInt(low, high + 1);
                    if(visited[randomNum]==0){
                        visited[randomNum] =1;
                        imgs[randomNum].setImageBitmap(bitmaps.get(pointer));
                        pointer++;
                    }

                }
                else{
                    break;
                }
            }
        }
        else if(gridSize==5){
            pointer=0;
            low=0;
            high=24;
            visited = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            while(true){
                int validate=0;
                for(int i=0;i<=24;i++){
                    validate += visited[i];
                }
                if(validate!=25){
                    int randomNum = ThreadLocalRandom.current().nextInt(low, high + 1);
                    if(visited[randomNum]==0){
                        visited[randomNum] =1;
                        imgs[randomNum].setImageBitmap(bitmaps.get(pointer));
                        pointer++;
                    }

                }
                else{
                    break;
                }
            }
        }

    }


}
