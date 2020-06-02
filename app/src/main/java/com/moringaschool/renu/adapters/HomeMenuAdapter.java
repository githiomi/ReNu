package com.moringaschool.renu.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.renu.CheckoutActivity;
import com.moringaschool.renu.GridViewClass;
import com.moringaschool.renu.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeMenuAdapter extends BaseAdapter {

    private static final String TAG = "Home Menu Adapter";
    //    Required variable
    public Context mContext;
    public Typeface mTypeFace;
    private List<GridViewClass> orders;
    private List<GridViewClass> ordersFiltered;


    public HomeMenuAdapter (Context context, List<GridViewClass> gridViewClassList, Typeface typeface){
        this.mContext = context;
        this.orders = gridViewClassList;
        this.ordersFiltered = gridViewClassList;
        this.mTypeFace = typeface;
    }

    @Override
    public int getCount() {
        return ordersFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if ( convertView == null ) {

//            Pull the single meal grid
            gridView = inflater.inflate(R.layout.activity_meal_grid, null);

//            Pull sections to alter
            TextView orderName = gridView.findViewById(R.id.tvMealName);
            TextView orderPrice = gridView.findViewById(R.id.tvMealPrice);

            ImageView orderImage = gridView.findViewById(R.id.ivMealItem);
            Button button = gridView.findViewById(R.id.btnAddToCart);

//            Set new Values to these views
            orderName.setText(ordersFiltered.get(position).getFoodName());
            orderPrice.setText("Costs: " + ordersFiltered.get(position).getFoodPrice());
            orderPrice.setTypeface(mTypeFace);
            orderImage.setImageResource(ordersFiltered.get(position).getImage());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "You have ordered: " + ordersFiltered.get(position).getFoodName(), Toast.LENGTH_SHORT).show();

                    Intent checkoutPage = new Intent(mContext, CheckoutActivity.class);
                    checkoutPage.putExtra("item", ordersFiltered.get(position));

                    Log.d(TAG, ordersFiltered.get(position).getFoodName());

                }
            });

        }else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
