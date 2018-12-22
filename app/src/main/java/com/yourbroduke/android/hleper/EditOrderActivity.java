package com.yourbroduke.android.hleper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private int mTypeNumber;
    private int mCampusNumber;

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

        setupSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_default:
                // TODO: fill the editor texts with default value of the user
                return true;

            case R.id.action_save:
                // TODO: submit the order information to the server and process on the reply
                finish();
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the type and campus of the order
     */
    private void setupSpinner() {
        // Create adapter for spinners.
        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.order_type_options, android.R.layout.simple_spinner_item);
        ArrayAdapter campusSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.order_campus_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        campusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTypeSpinner.setAdapter(typeSpinnerAdapter);
        mCampusSpinner.setAdapter(campusSpinnerAdapter);


        // TODO: Use TYPE_UNKNOWN.. to replace devil number
        // Set the integer mSelected to the constant values
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTypeNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mTypeNumber = 0; // Unknown
            }
        });
        mCampusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCampusNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mCampusNumber = 0; // Unknown
            }
        });
    }
}
