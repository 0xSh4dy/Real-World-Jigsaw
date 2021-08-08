package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    Intent registerIntent ;
    Intent homeIntent;
    GifImageView gifImageViewMain;
    ConnectivityManager cm;
    SharedPreferences preferences;
    SharedPreferences pref1;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button loginButton;
    private TextView ResponseTextView;
    TextView registerTextView;
    private final String loginUrl = "https://jigsaw-real.herokuapp.com/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("The Real Jigsaw");
        registerIntent= new Intent(getApplicationContext(),RegisterActivity.class);
        registerTextView = findViewById(R.id.registerTextView);
        gifImageViewMain = findViewById(R.id.gifImageView3);
        gifImageViewMain.setVisibility(View.GONE);
        cm =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        nameEditText = findViewById(R.id.nameEditText1);
        passwordEditText = findViewById(R.id.passEditText1);
        emailEditText = findViewById(R.id.emailEditText1);
        loginButton=findViewById(R.id.loginButton);
        ResponseTextView = findViewById(R.id.responseTV);
        homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
        pref1 = this.getSharedPreferences("auth",MODE_PRIVATE);
        String loggedIn = pref1.getString("loggedIn","no");
        if(loggedIn.equals("yes")){
            String n = pref1.getString("username","");
            homeIntent.putExtra("name",n);
            startActivity(homeIntent);
            finish();
        }

        registerTextView.setOnClickListener(v -> startActivity(registerIntent));
    }
    public void Login(View view){
        String username = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        if(cm.getActiveNetworkInfo()!=null){
            RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());
            if(username.isEmpty()){
                nameEditText.setError("Enter username");
            }
            else if(password.isEmpty()){
                passwordEditText.setError("Enter password");
            }
            else if(email.isEmpty()){
                emailEditText.setError("Enter email");
            }
            else{
                gifImageViewMain.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                registerTextView.setVisibility(View.GONE);
                StringRequest newStringRequest = new StringRequest(Request.Method.POST, loginUrl,
                        response -> {
                            if (response.equals("Success")) {
                                preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username",username);
                                editor.putString("loggedIn","yes");
                                editor.remove("loggedOut");
                                editor.apply();
                                homeIntent.putExtra("name",username);
                                startActivity(homeIntent);
                                finish();
                            }
                            else if(response.equals("No account")){
                                String text = "No account exists with that email";
                                ResponseTextView.setText(text);
                                gifImageViewMain.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                                registerTextView.setVisibility(View.VISIBLE);
                            }
                            else {
                                ResponseTextView.setText(response);
                                gifImageViewMain.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                                registerTextView.setVisibility(View.VISIBLE);

                            }
                        },
                        error -> {
                            gifImageViewMain.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.GONE);
                            registerTextView.setVisibility(View.GONE);
                            StringRequest newStringRequest1 = new StringRequest(Request.Method.POST, loginUrl,
                                    response -> {
                                        if (response.equals("Success")) {
                                            preferences = this.getSharedPreferences("auth",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("username",username);
                                            editor.putString("loggedIn","yes");
                                            editor.remove("loggedOut");
                                            editor.apply();
                                            homeIntent.putExtra("name",username);
                                            startActivity(homeIntent);
                                            finish();
                                        }
                                        else if(response.equals("No account")){
                                            String text = "No account exists with that email";
                                            ResponseTextView.setText(text);
                                            gifImageViewMain.setVisibility(View.GONE);
                                            loginButton.setVisibility(View.VISIBLE);
                                            registerTextView.setVisibility(View.VISIBLE);

                                        }
                                        else {
                                            ResponseTextView.setText(response);
                                            gifImageViewMain.setVisibility(View.GONE);
                                            loginButton.setVisibility(View.VISIBLE);
                                            registerTextView.setVisibility(View.VISIBLE);

                                        }
                                    },
                                    error1 -> {
                        Toast.makeText(MainActivity.this, "Network error,please try again", Toast.LENGTH_LONG).show();
                        gifImageViewMain.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);
                        registerTextView.setVisibility(View.VISIBLE);

                                    }){
                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> loginParams = new HashMap<>();
                                    loginParams.put("username",username);
                                    loginParams.put("password",password);
                                    loginParams.put("email",email);
                                    return loginParams;
                                }
                            };
                            myQueue.add(newStringRequest1);
                        }){
                  @Override
                  protected Map<String,String> getParams(){
                      Map<String,String> loginParams = new HashMap<>();
                      loginParams.put("username",username);
                      loginParams.put("password",password);
                      loginParams.put("email",email);
                      return loginParams;
                  }
                };
                myQueue.add(newStringRequest);
            }
        }
        else{
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            String text = "No internet";
            ResponseTextView.setText(text);
        }


    }


}