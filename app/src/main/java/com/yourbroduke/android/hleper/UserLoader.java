package com.yourbroduke.android.hleper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.yourbroduke.android.hleper.data.HleperUser;
import com.yourbroduke.android.hleper.data.ListOrderItem;

import java.util.List;

public class UserLoader extends AsyncTaskLoader<HleperUser> {

    private static final String LOG_TAG = OrderLoader.class.getName();

    private String mUrl;

    UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.d(LOG_TAG, mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public HleperUser loadInBackground() {
        if (mUrl == null)
            return null;

        return QueryUtils.fetchUserData(mUrl);
    }
}
