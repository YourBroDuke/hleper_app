package com.yourbroduke.android.hleper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.yourbroduke.android.hleper.data.ListOrderItem;

import java.util.List;

public class OrderLoader extends AsyncTaskLoader<List<ListOrderItem>> {

    private static final String LOG_TAG = OrderLoader.class.getName();

    private String mUrl;

    OrderLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.d(LOG_TAG, mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ListOrderItem> loadInBackground() {
        if (mUrl == null)
            return null;

        return QueryUtils.fetchOrdersData(mUrl);
    }
}
