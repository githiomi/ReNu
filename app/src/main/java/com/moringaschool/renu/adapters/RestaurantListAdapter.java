package com.moringaschool.renu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.R;
import com.moringaschool.renu.ui.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    private List<Business> mRestaurants;
    private Context mContext;

    public RestaurantListAdapter(Context context, List<Business> restaurants){
        mContext = context;
        mRestaurants = restaurants;
    }

    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position){
        holder.bindRestaurant(mRestaurants.get(position));
    }

    @Override
    public int getItemCount(){
        return mRestaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.restaurantImageView) ImageView mRestaurantImageView;
        @BindView(R.id.restaurantNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;

        private Context mContext;

        public RestaurantViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindRestaurant(Business restaurant) {

            final int MAX_WIDTH = 200;
            final int MAX_HEIGHT = 200;

            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategories().get(0).getTitle());
            if (mRestaurantImageView == null) {
                mRestaurantImageView.setImageResource(R.drawable.gradient);
            } else{
                Picasso.get().load(restaurant.getImageUrl())
                        .resize(MAX_WIDTH, MAX_HEIGHT)
                        .centerCrop()
                        .into(mRestaurantImageView);
            }
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("restaurants", Parcels.wrap(mRestaurants));
            mContext.startActivity(intent);
        }
    }
}
