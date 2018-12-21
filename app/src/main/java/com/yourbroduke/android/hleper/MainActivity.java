package com.yourbroduke.android.hleper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        Button signInOrOutBtn = (Button) findViewById(R.id.main_signInOrOut);
        signInOrOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent= new Intent(MainActivity.this, SignInActivity.class);

                startActivity(logInIntent);
            }
        });

        ImageButton taxiBtn = (ImageButton) findViewById(R.id.imBtn_tl);
        taxiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByType(1);
            }
        });
        ImageButton cheerBtn = (ImageButton) findViewById(R.id.imBtn_tr);
        cheerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByType(2);
            }
        });
        ImageButton bagBtn = (ImageButton) findViewById(R.id.imBtn_bl);
        bagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByType(3);
            }
        });
        ImageButton shakeBtn = (ImageButton) findViewById(R.id.imBtn_br);
        shakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByType(4);
            }
        });
    }

    private void startActivityByType(int type) {
        Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        intent.putExtra("Message", bundle);
        startActivity(intent);
    }
}
