package com.moringaschool.renu.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.renu.R;
import com.moringaschool.renu.models.ApiConstants;
import com.moringaschool.renu.models.Restaurant;
import com.moringaschool.renu.ui.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    Local variables
    View mView;
    Context mContext;

    public FirebaseOrderViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRestaurant(Restaurant restaurant) {
        ImageView restaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.restaurantNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);

        Picasso.get().load(restaurant.getImageUrl()).into(restaurantImageView);

        nameTextView.setText(restaurant.getName());
        categoryTextView.setText(restaurant.getCategories().get(0).toString());
    }

    @Override
    public void onClick(View view) {
//        Nothing is being clicked atm
    }

}
