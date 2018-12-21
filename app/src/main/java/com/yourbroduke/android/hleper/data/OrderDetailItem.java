package com.yourbroduke.android.hleper.data;

/**
 * Created by CSsol on 2018-12-02.
 */

public class OrderDetailItem extends ListOrderItem {

    // The creator's name string
    private String mCreatorName;
    // The creator's id
    private int mCreatorID;
    // The detail description
    private String mDescriptionLong;

    public OrderDetailItem() {
        // do nothing
    }

    public OrderDetailItem(String creatorName, int creatorID, String descriptionLong,
                           int ID, int type, int totalPeople, int currentPeople,
                           double reward, String title, String descriptionShort) {
        mCreatorName = creatorName;
        mCreatorID = creatorID;
        mDescriptionLong = descriptionLong;
        setmID(ID);
        setmType(type);
        setmTotalPeople(totalPeople);
        setmCurrentPeople(currentPeople);
        setmReward(reward);
        setmTitle(title);
        setmDescriptionShort(descriptionShort);
    }


    public String getmCreatorName() {
        return mCreatorName;
    }

    public void setmCreatorName(String mCreatorName) {
        this.mCreatorName = mCreatorName;
    }

    public int getmCreatorID() {
        return mCreatorID;
    }

    public void setmCreatorID(int mCreatorID) {
        this.mCreatorID = mCreatorID;
    }

    public String getmDescriptionLong() {
        return mDescriptionLong;
    }

    public void setmDescriptionLong(String mDescriptionLong) {
        this.mDescriptionLong = mDescriptionLong;
    }
}
