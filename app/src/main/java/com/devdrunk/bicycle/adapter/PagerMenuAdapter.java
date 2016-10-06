package com.devdrunk.bicycle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.devdrunk.bicycle.fragment.HistoryFragment;
import com.devdrunk.bicycle.fragment.RecordFragment;

/**
 * Created by CRRU0001 on 06/10/2559.
 */

public class PagerMenuAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_NUM = 2;
    public PagerMenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RecordFragment.newInstance();
            case 1:
                return HistoryFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Record";
            case 1:
                return "History";
            default:
                return "";

        }
    }
}
