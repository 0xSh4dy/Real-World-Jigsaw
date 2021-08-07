package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Reset Credentials");
        Button resetPass = findViewById(R.id.resetpass);
        EditText emailEt = findViewById(R.id.editTextTextEmailAddress);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        TextView textView = findViewById(R.id.textView9);
        String loginUrl = "https://jigsaw-real.herokuapp.com/reset/em";
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cm.getActiveNetworkInfo() != null) {
                    String text = emailEt.getText().toString();
                    if (text.isEmpty()) {
                        emailEt.setError("Cannot be empty");
                    } else {
                        resetPass.setVisibility(View.GONE);
                        RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest newStringRequest = new StringRequest(Request.Method.POST, loginUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("Done")) {
                                            textView.setText("An email containing the reset link has been sent. Check your spam folder");

                                        }
                                        else if(response.equals("Invalid")){
                                            textView.setText("Invalid email provided");
                                            resetPass.setVisibility(View.VISIBLE);
                                        }
                                        else if(response.equals("No")){
                                            resetPass.setVisibility(View.VISIBLE);
                                            textView.setText("Oops, there was some error. Please try again");
                                        }
                                        else if(response.equals("Error")){
                                            textView.setText("Oops, there was some error. Please try again");
                                            resetPass.setVisibility(View.VISIBLE);
                                        }                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        StringRequest newStringRequest1 = new StringRequest(Request.Method.POST, loginUrl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("Done")) {

                                                            textView.setText("Reset mail has been sent to your email. Check your spam folder");
                                                        }
                                                        else if(response.equals("Invalid")){
                                                            textView.setText("Invalid email provided");
                                                            resetPass.setVisibility(View.VISIBLE);
                                                        }
                                                        else if(response.equals("No")){
                                                            resetPass.setVisibility(View.VISIBLE);
                                                            textView.setText("Oops, there was some error. Please try again");
                                                        }
                                                        else if(response.equals("Error")){
                                                            textView.setText("Oops, there was some error. Please try again");
                                                            resetPass.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getApplicationContext(), "Network error,please try again", Toast.LENGTH_LONG).show();

                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> loginParams = new HashMap<String, String>();
                                                loginParams.put("username", text);

                                                return loginParams;
                                            }
                                        };
                                        myQueue.add(newStringRequest1);
                                    }

                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> loginParams = new HashMap<String, String>();
                                loginParams.put("email", text);

                                return loginParams;
                            }
                        };
                        myQueue.add(newStringRequest);
                    }
                }
                else{
                    Toast.makeText(ChangePassword.this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}