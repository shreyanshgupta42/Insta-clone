/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{

  TextView textView2;
  Button button;
  RelativeLayout layout;
  ImageView imageView;
  boolean signupactive=true;

  public void showlist(){
    Intent intent=new Intent(getApplicationContext(),secondActivity.class);
    startActivity(intent);
  }

  public void onClick(View view){
    if(view.getId()==R.id.textView) {
      if (signupactive) {
        button.setText("LOG IN");
        textView2.setText("or SIGN UP");
        signupactive = false;
      } else {
        button.setText("SIGN UP");
        textView2.setText("or LOG IN");
        signupactive = true;
      }
    }else if(view.getId()==R.id.imageView || view.getId()==R.id.relativelayout){
      InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {

    if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
      onclick(v);
    }

    return false;
  }

  public void onclick(View view){
    EditText usernameEditText=(EditText)findViewById(R.id.editText1);
    EditText passwordEditText=(EditText)findViewById(R.id.editText2);

    if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
      Toast.makeText(getApplicationContext(),"please provide your username and password",Toast.LENGTH_SHORT).show();
    }else {
      if(signupactive) {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(usernameEditText.getText().toString());
        parseUser.setPassword(passwordEditText.getText().toString());
        parseUser.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
              showlist();
            } else {
              Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }else{
//        ParseUser parseUser = new ParseUser();
//        parseUser.get("username",)
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            Log.d("login","inside login in background");
            Log.d("info",usernameEditText.getText().toString()+" "+passwordEditText.getText().toString());
            if(user!=null){
              Toast.makeText(getApplicationContext(),"you are successfully logged in",Toast.LENGTH_SHORT).show();
              showlist();
            }else{
              Log.d("login error","username or password is incorrect, try again"+e.getMessage());
              Toast.makeText(getApplicationContext(),"username or password is incorrect, try again",Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView2=findViewById(R.id.textView);
    button=findViewById(R.id.button2);
    layout=findViewById(R.id.relativelayout);
    imageView=findViewById(R.id.imageView);
    imageView.setOnClickListener(this);
    layout.setOnClickListener(this);

    if(ParseUser.getCurrentUser()!=null && ParseUser.getCurrentUser().getUsername()!=null){
      showlist();
    }

    textView2.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}