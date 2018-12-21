package com.yourbroduke.android.hleper.data;

/**
 * Created by CSsol on 2018-11-29.
 * The information of the order item to show on the list
 */

public class ListOrderItem {
    // The ID of the order
    private int mID;

    // The type of the order
    private int mType;

    // The total number of people this order needs
    private int mTotalPeople;

    // The current number of people this order has
    private int mCurrentPeople;

    // The amount of money this order gives
    private double mReward;

    // The title of this order
    private String mTitle;

    // The short description of this order to show on the list
    private String mDescriptionShort;

    public ListOrderItem() {
        // do nothing
    }

    public ListOrderItem(int ID, int type, int totalPeople, int currentPeople, double reward, String title, String descriptionShort) {
        mID = ID;
        mType = type;
        mTotalPeople = totalPeople;
        mCurrentPeople = currentPeople;
        mReward = reward;
        mTitle = title;
        mDescriptionShort = descriptionShort;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int ID) {
        mID = ID;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int type) {
        mType = type;
    }

    public int getmTotalPeople() {
        return mTotalPeople;
    }

    public void setmTotalPeople(int totalPeople) {
        mTotalPeople = totalPeople;
    }

    public int getmCurrentPeople() {
        return mCurrentPeople;
    }

    public void setmCurrentPeople(int currentPeople) {
        mCurrentPeople = currentPeople;
    }

    public double getmReward() {
        return mReward;
    }

    public void setmReward(double reward) {
        mReward = reward;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        mTitle = title;
    }

    public String getmDescriptionShort() {
        return mDescriptionShort;
    }

    public void setmDescriptionShort(String descriptionShort) {
        mDescriptionShort = descriptionShort;
    }
}
