package com.yourbroduke.android.hleper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.yourbroduke.android.hleper.data.ListOrderItem;
import com.yourbroduke.android.hleper.data.OrderDetailItem;

import java.util.List;

public class SpecificLoader extends AsyncTaskLoader<OrderDetailItem> {

    private static final String LOG_TAG = SpecificLoader.class.getName();

    private String mUrl;

    SpecificLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.d(LOG_TAG, mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public OrderDetailItem loadInBackground() {
        if (mUrl == null)
            return null;
        int begin = mUrl.indexOf('/', 10);
        int end = mUrl.indexOf('?');
        String queryType = mUrl.substring(begin+1, end);

        if (queryType.equals("specific")) {
            return QueryUtils.fetchSpecificData(mUrl);
        } else if (queryType.equals("order_plus")) {
            return QueryUtils.postOrderPlus(mUrl);
        } else if (queryType.equals("order_done")) {
            return QueryUtils.postOrderDone(mUrl);
        }
        return null;
    }
}
