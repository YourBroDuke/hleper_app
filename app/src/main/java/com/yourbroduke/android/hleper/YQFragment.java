package com.yourbroduke.android.hleper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.ListOrderItem;
import com.yourbroduke.android.hleper.data.ListOrderItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class YQFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ListOrderItem>>{

    private static final String ORDERS_QUERY_URL = "http://192.227.162.248/orders";
    private static final String LOG_TAG = YQFragment.class.getSimpleName();

    public YQFragment() {
        // Required empty public constructor
    }

    // The type of the order needed to be fetched
    private int mType;
    private ListOrderItemAdapter mAdapter;
    private int mCount;
    private SwipeRefreshLayout mRefreshLayout;
    private ViewPager mViewPager;
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the type of current activity
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getBundleExtra("Message");
        mType = bundle.getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_order_list, container, false);

        // Create an {@link ListOrderItemAdapter}, whose data source is a list of {@link ListOrderItem}s. The
        // adapter knows how to create list items for each item in the list.
        mAdapter = new ListOrderItemAdapter(getActivity(), new ArrayList<ListOrderItem>());
        mRefreshLayout = rootView.findViewById(R.id.pullToRefresh);
        mViewPager = getActivity().findViewById(R.id.viewpager);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        mRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });


        // Find the ListView in the xml to show orders
        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int currentOrderId = mAdapter.getItem(position).getmID();

                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", currentOrderId);
                intent.putExtra("Message", bundle);

                startActivity(intent);
            }
        });

        mLoaderManager = getActivity().getSupportLoaderManager();

        mLoaderManager.initLoader(2, null, this);

        mRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mLoaderManager.restartLoader(2, null, YQFragment.this);
                    }
                }
        );

        return rootView;
    }
    @Override
    public Loader<List<ListOrderItem>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(ORDERS_QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("limit", "25");
        uriBuilder.appendQueryParameter("campus", "2");

        return new OrderLoader(getActivity(), uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<ListOrderItem>> loader, List<ListOrderItem> orders) {
        mAdapter.clear();

        if (mRefreshLayout.isRefreshing())
            mRefreshLayout.setRefreshing(false);
        if (orders != null && !orders.isEmpty()) {
            mAdapter.addAll(orders);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ListOrderItem>> loader) {
        mAdapter.clear();
    }
}
