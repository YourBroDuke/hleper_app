package com.yourbroduke.android.hleper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_form);

        TextView signUpText = (TextView) findViewById(R.id.to_sign_up);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);

                startActivity(signUpIntent);
            }
        });
    }
}
