package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class Leaderboard extends AppCompatActivity {
    ConnectivityManager cm;
    WebView webView;
    Button reload;
    GifImageView loading2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setTitle("Leaderboard");
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        webView = findViewById(R.id.webView);
        reload = findViewById(R.id.reload);
        loading2 = findViewById(R.id.loading2);
        loadWebView();
        reload.setOnClickListener(v -> loadWebView());

    }
    @SuppressLint("SetJavaScriptEnabled")
    public void loadWebView(){
        if(cm.getActiveNetworkInfo()!=null){
            try{
                webView.loadUrl("https://jigsaw-real.herokuapp.com/");
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        loading2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        loading2.setVisibility(View.GONE);
                    }
                });


            }
            catch(Exception e){
                Toast.makeText(this, "There was some error,please reload ", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
        }
    }

}