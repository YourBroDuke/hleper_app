package com.yourbroduke.android.hleper.data;

public class HleperUser {

    private int mID;
    private String mName;
    private String mEmail;
    private String mPhoneNumber;
    private double mBalance;

    public HleperUser(int ID, String name, String email, String phoneNumber, float balance) {
        mID = ID;
        mName = name;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mBalance = balance;
    }

    public HleperUser() {
        mID = -1;
        mName = null;
        mEmail = null;
        mPhoneNumber = null;
        mBalance = -1;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public double getmBalance() {
        return mBalance;
    }

    public void setmBalance(double mBalance) {
        this.mBalance = mBalance;
    }
}
