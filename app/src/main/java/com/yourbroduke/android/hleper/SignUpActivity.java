package com.yourbroduke.android.hleper;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.HleperUser;
import com.yourbroduke.android.hleper.data.ListOrderItem;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HleperUser>{

    private static final String SIGN_QUERY_URL = "http://45.77.33.175/sign_up";
    private EditText emailAddressView, passwordView, repeatPasswordView;
    private LoaderManager mLoaderManager;
    boolean initFlag = true;

    @Override
    public Loader<HleperUser> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(SIGN_QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        String emailStr = emailAddressView.getText().toString().trim();
        String pwStr = passwordView.getText().toString().trim();

        uriBuilder.appendQueryParameter("email", emailStr);
        uriBuilder.appendQueryParameter("password", EncryptUtil.string2MD5(pwStr));

        return new UserLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<HleperUser> loader, HleperUser userInfo) {
        if (userInfo.getmID() == -1) {
            Toast.makeText(this, "该邮箱已被注册", Toast.LENGTH_LONG).show();
        }
        else {
            MainActivity.mainUser.setmID(userInfo.getmID());
            MainActivity.mainUser.setmName(userInfo.getmName());
            MainActivity.mainUser.setmEmail(userInfo.getmEmail());
            MainActivity.mainUser.setmPhoneNumber(userInfo.getmPhoneNumber());

            FileUtils.writeUserInfo(userInfo);

            finish();
        }
    }

    @Override
    public void onLoaderReset(Loader<HleperUser> loader) {
        // Do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);

        emailAddressView = (EditText) findViewById(R.id.up_edit_email);
        passwordView = (EditText) findViewById(R.id.up_edit_passwd);
        repeatPasswordView = (EditText) findViewById(R.id.up_edit_verify);

        mLoaderManager = getSupportLoaderManager();

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = emailAddressView.getText().toString().trim();
                String pwStr = passwordView.getText().toString().trim();
                String verifyStr = repeatPasswordView.getText().toString().trim();

                if (!pwStr.equals(verifyStr) || emailStr.isEmpty() || pwStr.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "请填写完整且保证两次密码相同", Toast.LENGTH_LONG).show();
                    return;
                }

                if (initFlag) {
                    mLoaderManager.initLoader(1, null, SignUpActivity.this);
                    initFlag = false;
                }
                else
                    mLoaderManager.restartLoader(1, null, SignUpActivity.this);
            }
        });


    }
}
