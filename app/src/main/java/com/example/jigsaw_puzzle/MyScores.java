package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MyScores extends AppCompatActivity {
    WebView scoreView;
    GifImageView loadingBg;
    GifImageView mainBg;
    ConnectivityManager cm;
    final String postNameUrl = "https://jigsaw-real.herokuapp.com/scoreboard/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scores);
        setTitle("My Scores");
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        scoreView = findViewById(R.id.scoreView);
        loadingBg = findViewById(R.id.loadingScores2);
        mainBg = findViewById(R.id.loadingScores);
        final String username = getIntent().getStringExtra("username");
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
                   mainBg.setVisibility(View.VISIBLE);
               }

               @Override
               public void onPageFinished(WebView view, String url) {
                   super.onPageFinished(view, url);
                   loadingBg.setVisibility(View.GONE);
                   mainBg.setVisibility(View.GONE);
               }
           });
        }
    }
}