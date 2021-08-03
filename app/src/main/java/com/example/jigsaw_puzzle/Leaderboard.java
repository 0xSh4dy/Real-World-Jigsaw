package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Leaderboard extends AppCompatActivity {
    ConnectivityManager cm;
    WebView webView;
    Button reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        webView = findViewById(R.id.webView);
        reload = findViewById(R.id.reload);
        loadWebView();
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loadWebView();

            }
        });

    }
    @SuppressLint("SetJavaScriptEnabled")
    public void loadWebView(){
        if(cm.getActiveNetworkInfo()!=null){
            try{
                webView.loadUrl("https://jigsaw-real.herokuapp.com/");
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());

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