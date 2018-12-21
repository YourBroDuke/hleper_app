package com.yourbroduke.android.hleper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("sign up", "onCreate: before");
        setContentView(R.layout.sign_up_form);
        Log.e("sign up", "onCreate: after");
    }
}
