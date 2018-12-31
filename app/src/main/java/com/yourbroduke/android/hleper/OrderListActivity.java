package com.yourbroduke.android.hleper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.yourbroduke.android.hleper.data.ListOrderItem;

import java.util.List;

public class OrderListActivity extends AppCompatActivity{

    private Toolbar mToolBar;
    private ViewPager mViewPager;
    SwipeRefreshLayout mRefreshLayout;

    private static final String ORDERS_QUERY_URL = "192.227.162.248:8000/orders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_main);

        // Get the type of current activity
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Message");
        final int flag = bundle.getInt("type");

        // Set up the tool bar with material drawer
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);

        DrawUtil.getDrawer(this, mToolBar, savedInstanceState);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        assert mViewPager != null;
        CampusAdapter campusAdapter = new CampusAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        mViewPager.setAdapter(campusAdapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderListActivity.this, EditOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", flag);
                intent.putExtra("Message", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
}
