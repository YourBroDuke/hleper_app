package com.yourbroduke.android.hleper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yourbroduke.android.hleper.data.ListOrderItem;
import com.yourbroduke.android.hleper.data.ListOrderItemAdapter;

import java.util.ArrayList;

public class ZJFragment extends Fragment {
    public ZJFragment() {
        // Required empty public constructor
    }

    // The type of the order needed to be fetched
    private int mFlag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the type of current activity
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getBundleExtra("Message");
        mFlag = bundle.getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_order_list, container, false);

        // Get the list of word needed to show
        final ArrayList<ListOrderItem> orders = getLatestOrders(mFlag, 4);

        // Create an {@link ListOrderItemAdapter}, whose data source is a list of {@link ListOrderItem}s. The
        // adapter knows how to create list items for each item in the list.
        ListOrderItemAdapter adapter = new ListOrderItemAdapter(getActivity(), orders);

        // Find the ListView in the xml to show orders
        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        return rootView;
    }

    private ArrayList<ListOrderItem> getLatestOrders(int flag, int num) {
        ArrayList<ListOrderItem> result = new ArrayList<>();
        result.add(new ListOrderItem(1, 3, 1, 0, 1.256,
                "帮我拿外卖", "玉泉5舍 129室 尾号2349"));
        result.add(new ListOrderItem(2, 2, 1, 0, 0.000,
                "一点点拼单", "玉泉5舍 129室"));
        result.add(new ListOrderItem(3, 1, 3, 1, 0.000,
                "紫金港->杭州东", "11月12号 中午12点出发"));
        result.add(new ListOrderItem(4, 4, 1, 0, 2.22,
                "买瓶酸奶", "玉泉5舍 129室"));
        return result;
    }
}
