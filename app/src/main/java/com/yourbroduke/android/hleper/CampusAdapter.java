package com.yourbroduke.android.hleper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by CSsol on 2018-12-02.
 * This adapter determine which fragment to be shown on the activity
 */


public class CampusAdapter extends FragmentPagerAdapter{

    /** Context of the app */
    private Context mContext;

    public CampusAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ZJGFragment();
        } else if (position == 1) {
            return new YQFragment();
        } else if (position == 2) {
            return new XXFragment();
        } else {
            return new ZJFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.campus_zjg);
        } else if (position == 1) {
            return mContext.getString(R.string.campus_yq);
        } else if (position == 2) {
            return mContext.getString(R.string.campus_xx);
        } else {
            return mContext.getString(R.string.campus_zj);
        }
    }
}
