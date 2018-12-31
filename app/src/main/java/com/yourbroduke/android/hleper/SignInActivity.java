package com.yourbroduke.android.hleper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.HleperUser;

public class SignInActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HleperUser>{

    private static final String LOG_QUERY_URL = "http://192.227.162.248/log_in";
    private EditText emailAddressView, passwordView;
    private LoaderManager mLoaderManager;
    boolean initFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_form);

        emailAddressView = (EditText) findViewById(R.id.in_edit_email);
        passwordView = (EditText) findViewById(R.id.in_edit_passwd);
        mLoaderManager = getSupportLoaderManager();

        TextView signUpText = (TextView) findViewById(R.id.to_sign_up);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);

                startActivity(signUpIntent);
            }
        });

        Button logInButton = (Button) findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = emailAddressView.getText().toString().trim();
                String pwStr = passwordView.getText().toString().trim();

                if (emailStr.isEmpty() || pwStr.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "请填写完整", Toast.LENGTH_LONG).show();
                    return;
                }

                if (initFlag) {
                    mLoaderManager.initLoader(1, null, SignInActivity.this);
                    initFlag = false;
                } else {
                    mLoaderManager.restartLoader(1, null, SignInActivity.this);
                }
            }
        });
    }

    @Override
    public Loader<HleperUser> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(LOG_QUERY_URL);
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
            Toast.makeText(this, "邮箱/用户名或密码错误", Toast.LENGTH_LONG).show();
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
}
