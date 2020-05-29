package com.moringaschool.renu.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.ui.RestaurantDetailFragment;

import java.util.List;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private List<Business> mRestaurants;

    public RestaurantPagerAdapter(FragmentManager fm, List<Business> restaurants) {
        super(fm);
        mRestaurants = restaurants;
    }

    @Override
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }

    @Override
    public int getCount() {
        return mRestaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRestaurants.get(position).getName();
    }
}