package com.yourbroduke.android.hleper.data;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourbroduke.android.hleper.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by CSsol on 2018-12-01.
 * This adapter list the data need to be show on the list
 */

public class ListOrderItemAdapter extends ArrayAdapter<ListOrderItem> {

    /** Resource ID for the image of the item **/
    private int mImgResource;

    public ListOrderItemAdapter(Context context, ArrayList<ListOrderItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_order_item, parent, false);
        }

        // Get the {@link ListOrderItem} object located at this position in the list
        ListOrderItem currentOrder = getItem(position);
        assert currentOrder != null;

        // Find the TextView in the list_order_item.xml layout with the ID info_title
        TextView titleText = listItemView.findViewById(R.id.info_title);
        // Set text with the title in current order item
        titleText.setText(currentOrder.getmTitle());

        // Find the TextView in the list_order_item.xml layout with the ID info_title
        TextView descriptionShortText = listItemView.findViewById(R.id.description_short);
        // Set text with the title in current order item
        descriptionShortText.setText(currentOrder.getmDescriptionShort());

        // Find the TextView in the list_order_item.xml layout with the ID info_title
        TextView rewardText = listItemView.findViewById(R.id.reward_data);
        // Get the string showed on the view
        String rewardStr = new DecimalFormat("#0.00").format(currentOrder.getmReward());
        // Set the text with the string being cast
        rewardText.setText(rewardStr);

        // Find the TextView in the list_order_item.xml layout with the ID item_ratio
        TextView ratioText = listItemView.findViewById(R.id.item_ratio);
        // Get the people ratio string of current order
        String ratioStr = currentOrder.getmCurrentPeople() + "/" + currentOrder.getmTotalPeople();
        // Set the text with the string being created
        ratioText.setText(ratioStr);
        if (currentOrder.getmCurrentPeople() == currentOrder.getmTotalPeople())
            ratioText.setTextColor(Color.rgb(255, 0, 0));
        else
            ratioText.setTextColor(Color.rgb(0,0,0));


        // Find the ImageView in the list_order_item.xml layout with the ID order_img
        ImageView imgView = listItemView.findViewById(R.id.order_img);
        // Get the img resource
        int imgSrc = getImgSrcByType(currentOrder.getmType());
        // Set the img  with the src id founded
        imgView.setImageResource(imgSrc);
        // Make sure the view is visible
        imgView.setVisibility(View.VISIBLE);

        // Return the whole list item layout so that it can be shown in the list view
        return listItemView;
    }

    /**
     * Get the img src by the type of the order
     *
     * @param type is the type of the order(i.e. taxi outer)
     * @return the src of the right image
     */
    private int getImgSrcByType(int type) {
        switch (type) {
            case 1: return R.drawable.taxi256;
            case 2: return R.drawable.toast256;
            case 3: return R.drawable.roastchicken256;
            case 4: return R.drawable.handshake256;
            default: return R.drawable.taxi256;
        }
    }
}
