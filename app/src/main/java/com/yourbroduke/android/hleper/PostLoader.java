package com.yourbroduke.android.hleper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.yourbroduke.android.hleper.data.ListOrderItem;

import java.util.List;
import java.util.Map;

public class PostLoader extends AsyncTaskLoader<String> {

    private static final String LOG_TAG = OrderLoader.class.getName();

    private String mUrl;
    private Map<String, String> mParams;

    PostLoader(Context context, String url, Map<String, String> params) {
        super(context);
        mUrl = url;
        mParams = params;
        Log.d(LOG_TAG, mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (mUrl == null)
            return null;

        return QueryUtils.postOrderData(mUrl, mParams);
    }
}
