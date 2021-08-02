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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    Intent registerIntent ;
    Intent homeIntent;
    SharedPreferences preferences;
    GifImageView gifImageViewMain;
    ConnectivityManager cm;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button loginButton;
    private TextView ResponseTextView;
    private boolean validCredentials;
    private final String loginUrl = "https://jigsaw-real.herokuapp.com/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerIntent= new Intent(getApplicationContext(),RegisterActivity.class);
        TextView registerTextView = findViewById(R.id.registerTextView);
        gifImageViewMain = findViewById(R.id.gifImageView3);
        gifImageViewMain.setVisibility(View.GONE);
        cm =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        nameEditText = findViewById(R.id.nameEditText1);
        passwordEditText = findViewById(R.id.passEditText1);
        emailEditText = findViewById(R.id.emailEditText1);
        loginButton=findViewById(R.id.loginButton);
        ResponseTextView = findViewById(R.id.responseTV);
        homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
        preferences = getSharedPreferences("userData,",MODE_PRIVATE);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);

            }
        });
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
                StringRequest newStringRequest = new StringRequest(Request.Method.POST, loginUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Success")) {
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    ResponseTextView.setText(response);
                                    gifImageViewMain.setVisibility(View.GONE);
                                    loginButton.setVisibility(View.VISIBLE);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                  @Override
                  protected Map<String,String> getParams(){
                      Map<String,String> loginParams = new HashMap<String,String>();
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
            ResponseTextView.setText("No internet");
        }


    }


}