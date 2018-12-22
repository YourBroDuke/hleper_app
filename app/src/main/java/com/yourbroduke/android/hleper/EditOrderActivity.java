package com.yourbroduke.android.hleper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class EditOrderActivity extends AppCompatActivity {

    /** All the variables connected to the components in order editor layout */
    private EditText mTitleEditText;
    private EditText mDetailEditText;
    private EditText mRewardEditText;
    private EditText mPeopleNumberEditText;

    private Spinner mTypeSpinner;
    private Spinner mCampusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        // Find all the relevant views that we will need to read user input from
        mTitleEditText = (EditText) findViewById(R.id.edit_order_title);
        mDetailEditText = (EditText) findViewById(R.id.edit_order_detail);
        mRewardEditText = (EditText) findViewById(R.id.edit_order_reward);
        mPeopleNumberEditText = (EditText) findViewById(R.id.edit_order_people_number);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_order_type);
        mCampusSpinner = (Spinner) findViewById(R.id.spinner_order_campus);

        // TODO: setupSpinner();
    }
}
