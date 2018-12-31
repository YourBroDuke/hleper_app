package com.yourbroduke.android.hleper;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.HleperUser;

import java.util.HashMap;
import java.util.Map;

public class EditOrderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private static final String POST_URL = "http://192.227.162.248/new_order";
    private LoaderManager mLoaderManager;
    boolean initFlag = true;

    /** All the variables connected to the components in order editor layout */
    private EditText mTitleEditText;
    private EditText mDescriptionText;
    private EditText mDetailEditText;
    private EditText mRewardEditText;
    private EditText mPeopleNumberEditText;
    private String mTitleStr, mDescriptionStr, mDetailStr, mRewardStr, mNumStr;
    private int mPeopleNum;
    private float mRewardNum;

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
        mDescriptionText = (EditText) findViewById(R.id.edit_order_description);
        mDetailEditText = (EditText) findViewById(R.id.edit_order_detail);
        mRewardEditText = (EditText) findViewById(R.id.edit_order_reward);
        mPeopleNumberEditText = (EditText) findViewById(R.id.edit_order_people_number);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_order_type);
        mCampusSpinner = (Spinner) findViewById(R.id.spinner_order_campus);

        mLoaderManager = getSupportLoaderManager();

        setupSpinner();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_default:
                // TODO: fill the editor texts with default value of the user
                return true;

            case R.id.action_save:

                mTitleStr = mTitleEditText.getText().toString().trim();
                mDescriptionStr = mDescriptionText.getText().toString().trim();
                mDetailStr = mDetailEditText.getText().toString().trim();
                mRewardStr = mRewardEditText.getText().toString().trim();
                mNumStr = mPeopleNumberEditText.getText().toString().trim();

                if (mTitleStr.isEmpty() || mDescriptionStr.isEmpty() || mDetailStr.isEmpty() || mRewardStr.isEmpty() || mNumStr.isEmpty() || mCampusNumber == 0 || mTypeNumber == 0) {
                    Toast.makeText(this, "请填写完整，类型和校区不能为unknown", Toast.LENGTH_LONG).show();
                }
                else {
                    mRewardNum = Float.parseFloat(mRewardStr);
                    mPeopleNum = Integer.parseInt(mNumStr);

                    if (initFlag) {
                        mLoaderManager.initLoader(1, null, this);
                        initFlag = false;
                    } else
                        mLoaderManager.restartLoader(1, null, this);
                }
                finish();
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_editor, menu);
        return true;
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

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        Map<String, String> params = new HashMap<String, String>() {
            {
                put("order_type", mTypeNumber+"");
                put("order_campus", mCampusNumber+"");
                put("order_title", mTitleStr);
                put("order_description", mDescriptionStr);
                put("order_detail", mDetailStr);
                put("order_max_num", mNumStr);
                put("order_reward", mRewardStr);
                put("order_user_id", MainActivity.mainUser.getmID()+"");
            }
        };

        return new PostLoader(this, POST_URL, params);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data == null || !data.equals("Success")) {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "订单成功发起", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // Do nothing
    }
}
