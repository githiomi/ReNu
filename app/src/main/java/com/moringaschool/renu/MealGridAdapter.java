package com.moringaschool.renu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MealGridAdapter extends BaseAdapter {

    private Context mContext;
    public String[] mMealNames;
    public int[] mMealPrices;


    public MealGridAdapter(Context context, String[] mealNames, int[] mealPrices){
        this.mContext = context;
        this.mMealNames = mealNames;
        this.mMealPrices = mealPrices;
    }

    @Override
    public int getCount() {
        return mMealNames.length;
    }

    @Override
    public Object getItem(int position) {
        String mealName = mMealNames[position];
        int mealPrice = mMealPrices[position];

        return mealName;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.activity_meal_grid, null);

            TextView mealName = (TextView) gridView.findViewById(R.id.tvMealName);
            TextView mealPrice = (TextView) gridView.findViewById(R.id.tvMealPrice);
            ImageView mealImage = (ImageView) gridView.findViewById(R.id.ivMealItem);

            mealName.setText(getItem(position).toString());
        }
        else{
            gridView = (View) convertView;
        }
        return gridView;
    }
}
