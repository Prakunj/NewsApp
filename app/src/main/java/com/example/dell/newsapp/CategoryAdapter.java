package com.example.dell.newsapp;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }
    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new TopNewFragment();
        }else {
            return new TopicsFragment();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.top);
        } else {
            return mContext.getString(R.string.topics);
        }
    }
}
