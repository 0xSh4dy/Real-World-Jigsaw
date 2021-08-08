package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import pl.droidsonroids.gif.GifImageView;

public class MyScores extends AppCompatActivity {
    WebView scoreView;
    GifImageView loadingBg;
    ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scores);
        setTitle("My Scores");
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        scoreView = findViewById(R.id.scoreView);
        loadingBg = findViewById(R.id.loadingScores2);
        scoreView.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
        final String username = preferences.getString("username","");
        final String scoreUrl = "https://jigsaw-real.herokuapp.com/scoreboard/users/"+username;
        if(cm.getActiveNetworkInfo()==null){
            Toast.makeText(this,"Error. No internet connection",Toast.LENGTH_LONG).show();
        }
        else{
           scoreView.loadUrl(scoreUrl);
           scoreView.setWebViewClient(new WebViewClient(){
               @Override
               public void onPageStarted(WebView view, String url, Bitmap favicon) {
                   super.onPageStarted(view, url, favicon);
                   loadingBg.setVisibility(View.VISIBLE);
               }

               @Override
               public void onPageFinished(WebView view, String url) {
                   super.onPageFinished(view, url);
                   loadingBg.setVisibility(View.GONE);
                   scoreView.setVisibility(View.VISIBLE);
               }
           });
        }
    }
}