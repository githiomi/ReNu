package com.moringaschool.renu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeMenuAdapter extends BaseAdapter {

//    Order class to save complex object
    public class Order{

        public String foodName;
        public int foodPrice;

        public Order(String food, int price){
            this.foodName = food;
            this.foodPrice = price;
        }
    }

//    Required variable
    public Context mContext;
    public String[] mFoods;
    public int[] mPrices;
    public ArrayList<Order> orders;

    public HomeMenuAdapter (Context context, String[] foods, int[] prices){
        this.mContext = context;
        this.mFoods = foods;
        this.mPrices = prices;

//        for (int i = 0; i < mFoods.length; i += 1){
//            String foodName = mFoods[i];
//            int foodPrice = mPrices[i];
//
//            Order newOrder = new Order(foodName, foodPrice);
//            orders.add(newOrder);
//        }
    }

    @Override
    public int getCount() {
        return mFoods.length;
    }

    @Override
    public Object getItem(int position) {
        return mFoods[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if ( convertView == null ){

//            Pull the single meal grid
            gridView = inflater.inflate(R.layout.activity_meal_grid, null);

//            Pull sections to alter
            TextView orderName = gridView.findViewById(R.id.tvMealName);

//            Set new Values to these views
            orderName.setText(mFoods[position]);

        }else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
