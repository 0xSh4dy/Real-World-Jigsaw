package com.example.jigsaw_puzzle;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Button resetPass = findViewById(R.id.resetpass);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        TextView textView = findViewById(R.id.textView9);
        String loginUrl = "https://jigsaw-real.herokuapp.com/reset/em";
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest newStringRequest = new StringRequest(Request.Method.POST, loginUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Done")) {
                                    Toast.makeText(ChangePassword.this, "Done", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                StringRequest newStringRequest1 = new StringRequest(Request.Method.POST, loginUrl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (response.equals("Done")) {
                                                    Toast.makeText(ChangePassword.this, "done", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getApplicationContext(), "Network error,please try again", Toast.LENGTH_LONG).show();

                                            }
                                        }){
                                    @Override
                                    protected Map<String,String> getParams(){
                                        Map<String,String> loginParams = new HashMap<String,String>();
                                        loginParams.put("username","rakshit_a@ch.iitr.ac.in");

                                        return loginParams;
                                    }
                                };
                                myQueue.add(newStringRequest1);
                            }

                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> loginParams = new HashMap<String,String>();
                        loginParams.put("email","rakshit_a@ch.iitr.ac.in");

                        return loginParams;
                    }
                };
                myQueue.add(newStringRequest);
            }
            
        });
    }
}