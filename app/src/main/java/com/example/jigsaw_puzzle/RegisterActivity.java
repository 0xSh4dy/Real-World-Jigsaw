package com.example.jigsaw_puzzle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {
    public Button completeRegister;
    EditText emailEditText;
    EditText passwordEditText;
    EditText unameEditText;
    TextView textView;
    ConnectivityManager connectivityManager;
    GifImageView loading1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("The Real Jigsaw");
        completeRegister = findViewById(R.id.completeRegister);
        emailEditText = findViewById(R.id.regEmail);
        unameEditText = findViewById(R.id.regUsername);
        passwordEditText = findViewById(R.id.regPassword);
        textView = findViewById(R.id.textView16);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        loading1 = findViewById(R.id.loading1);
        loading1.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void redirectHome(View view) {
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
                        response -> {
                            textView.setText(response);
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            unameEditText.setText("");
                            if(response.equals("Successfully registered")){
                                Toast.makeText(RegisterActivity.this, "Successfully registered. Sign in to play the game", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(this,response,Toast.LENGTH_LONG).show();
                                completeRegister.setVisibility(View.VISIBLE);
                                loading1.setVisibility(View.INVISIBLE);
                            }

                        }, error -> {
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url,
                                    response -> {
                                        textView.setText(response);
                                        emailEditText.setText("");
                                        passwordEditText.setText("");
                                        unameEditText.setText("");
                                        if(response.equals("Successfully registered")){
                                            Toast.makeText(RegisterActivity.this, "Successfully registered. Sign in to play the game", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            completeRegister.setVisibility(View.VISIBLE);
                                            loading1.setVisibility(View.INVISIBLE);
                                        }

                                    }, error1 -> {
                                        Toast.makeText(RegisterActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                                        loading1.setVisibility(View.INVISIBLE);
                                        completeRegister.setVisibility(View.VISIBLE);
                                            })
                            {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("username", uName);
                                    params.put("password", uPass);
                                    params.put("email",uEmail);

                                    return params;
                                }
                            };

                            queue.add(stringRequest1);


                        }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
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
