package com.example.jigsaw_puzzle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import pl.droidsonroids.gif.GifImageView;

public class RegisterActivity extends AppCompatActivity {
    public Button completeRegister;
    final String base_url = "http://localhost:3500";
    EditText emailEditText;
    EditText passwordEditText;
    EditText unameEditText;
    TextView textView;
    private boolean redirectToHome = false;
    private String responseData;
    ConnectivityManager connectivityManager;
    GifImageView loading1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        completeRegister =(Button)findViewById(R.id.completeRegister);
        emailEditText = findViewById(R.id.regEmail);
        unameEditText = findViewById(R.id.regUsername);
        passwordEditText = findViewById(R.id.regPassword);
        textView = findViewById(R.id.textView);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        loading1 = findViewById(R.id.loading1);
        loading1.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void redirectHome(View view) throws IOException {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        String url = "https://jigsaw-real.herokuapp.com/register";
        String uName = unameEditText.getText().toString();
        String uPass = passwordEditText.getText().toString();
        String uEmail = emailEditText.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        if(connectivityManager.getActiveNetwork()==null){
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
        }
        if(uName.isEmpty()){
            unameEditText.setError("Enter username");
        }
        else if(uPass.isEmpty()){
            passwordEditText.setError("Enter password");
        }
        else if(uEmail.isEmpty()){
            emailEditText.setError("Enter email");
        }
        else{
            completeRegister.setVisibility(View.INVISIBLE);
            loading1.setVisibility(View.VISIBLE);
            if(connectivityManager.getActiveNetworkInfo()!=null){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            textView.setText(response);
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            unameEditText.setText("");
                            if(response.equals("Successfully registered")){
                                startActivity(intent);
                                finish();
                            }
                            else{
                                responseData = response;
                                completeRegister.setVisibility(View.VISIBLE);
                                loading1.setVisibility(View.INVISIBLE);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", uName);
                    params.put("password", uPass);
                    params.put("email",uEmail);

                    return params;
                }
            };

            queue.add(stringRequest);


        }
        else{
            Toast.makeText(this,"Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            loading1.setVisibility(View.GONE);
            completeRegister.setVisibility(View.VISIBLE);
        }

    }
    }
}
