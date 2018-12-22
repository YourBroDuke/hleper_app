package com.yourbroduke.android.hleper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourbroduke.android.hleper.data.OrderDetailItem;

import java.text.DecimalFormat;

public class OrderDetailActivity extends AppCompatActivity {

    // Current ID of the order detail
    private int mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Bundle bundle = getIntent().getBundleExtra("Message");
        mID = bundle.getInt("id");

        OrderDetailItem orderDetailItem = getOrderItemByID(mID);

        // Find the ImageView in the activity_order_detail.xml layout with the ID detail_img
        ImageView imgView = (ImageView) findViewById(R.id.detail_img);
        // Get the img resource
        int imgSrc = getImgSrcByType(orderDetailItem.getmType());
        // Set the img  with the src id founded
        imgView.setImageResource(imgSrc);
        // Make sure the view is visible
        imgView.setVisibility(View.VISIBLE);

        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        TextView titleText = (TextView) findViewById(R.id.detail_title);
        // Set text with the title in current order item
        titleText.setText(orderDetailItem.getmTitle());

        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        TextView creatorText = (TextView) findViewById(R.id.creator_name);
        // Set text with the title in current order item
        String creatorNameToBeShown = "发起用户："+orderDetailItem.getmCreatorName();
        creatorText.setText(creatorNameToBeShown);

        // Find the TextView in the list_order_item.xml layout with the ID info_title
        TextView rewardText = (TextView) findViewById(R.id.reward_data);
        // Get the string showed on the view
        String rewardStr = new DecimalFormat("#0.00").format(orderDetailItem.getmReward());
        // Set the text with the string being cast
        rewardText.setText(rewardStr);

        // Find the TextView in the list_order_item.xml layout with the ID item_ratio
        TextView ratioText = (TextView) findViewById(R.id.item_ratio);
        // Get the people ratio string of current order
        String ratioStr = orderDetailItem.getmCurrentPeople() + "/" + orderDetailItem.getmTotalPeople();
        // Set the text with the string being created
        ratioText.setText(ratioStr);

        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        TextView detailText = (TextView) findViewById(R.id.detail_detail);
        // Set text with the title in current order item
        String detailBeShown = "详细信息："+orderDetailItem.getmDescriptionLong();
        detailText.setText(detailBeShown);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private OrderDetailItem getOrderItemByID(int id) {
        OrderDetailItem orderDetailItem = new OrderDetailItem("YourBroDuke", 0, "暂无",
                id, 1, 1, 0, 1.28, "帮我拿外卖", "这个介绍十分短暂");

        return orderDetailItem;
    }

    /**
     * Get the img src by the type of the order
     *
     * @param type is the type of the order(i.e. taxi outer)
     * @return the src of the right image
     */
    private int getImgSrcByType(int type) {
        switch (type) {
            case 1: return R.drawable.taxi512;
            case 2: return R.drawable.toast512;
            case 3: return R.drawable.roastchicken512;
            case 4: return R.drawable.handshake512;
            default: return R.drawable.taxi512;
        }
    }
}
