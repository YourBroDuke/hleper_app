package com.yourbroduke.android.hleper;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.ListOrderItem;
import com.yourbroduke.android.hleper.data.OrderDetailItem;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<OrderDetailItem>{

    private static final String SPECIFIC_QUERY_URL = "http://192.227.162.248/specific";

    // Current ID of the order detail
    private int mID;
    private ImageView imgView;
    private TextView titleText, creatorText, rewardText, ratioText, detailText;
    private LoaderManager mLoaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Bundle bundle = getIntent().getBundleExtra("Message");
        mID = bundle.getInt("id");

        // Find the ImageView in the activity_order_detail.xml layout with the ID detail_img
        imgView = (ImageView) findViewById(R.id.detail_img);
        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        titleText = (TextView) findViewById(R.id.detail_title);
        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        creatorText = (TextView) findViewById(R.id.creator_name);
        // Find the TextView in the list_order_item.xml layout with the ID info_title
        rewardText = (TextView) findViewById(R.id.reward_data);
        // Find the TextView in the list_order_item.xml layout with the ID item_ratio
        ratioText = (TextView) findViewById(R.id.item_ratio);
        // Find the TextView in the activity_order_detail.xml layout with the ID detail_title
        detailText = (TextView) findViewById(R.id.detail_detail);

        mLoaderManager = getSupportLoaderManager();

        mLoaderManager.initLoader(3, null, this);
    }

    @Override
    public Loader<OrderDetailItem> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(SPECIFIC_QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("order_id", ""+mID);

        return new SpecificLoader(this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<OrderDetailItem> loader, OrderDetailItem orderDetailItem) {

        // Get the img resource
        int imgSrc = getImgSrcByType(orderDetailItem.getmType());
        // Set the img  with the src id founded
        imgView.setImageResource(imgSrc);
        // Make sure the view is visible
        imgView.setVisibility(View.VISIBLE);

        // Set text with the title in current order item
        titleText.setText(orderDetailItem.getmTitle());

        // Set text with the title in current order item
        String creatorNameToBeShown = "发起用户："+orderDetailItem.getmCreatorName();
        creatorText.setText(creatorNameToBeShown);

        // Get the string showed on the view
        String rewardStr = new DecimalFormat("#0.00").format(orderDetailItem.getmReward());
        // Set the text with the string being cast
        rewardText.setText(rewardStr);

        // Get the people ratio string of current order
        String ratioStr = orderDetailItem.getmCurrentPeople() + "/" + orderDetailItem.getmTotalPeople();
        // Set the text with the string being created
        ratioText.setText(ratioStr);

        // Set text with the title in current order item
        String detailBeShown = "详细信息："+orderDetailItem.getmDescriptionLong();
        detailText.setText(detailBeShown);
    }

    @Override
    public void onLoaderReset(Loader<OrderDetailItem> loader) {
        // Do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
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
