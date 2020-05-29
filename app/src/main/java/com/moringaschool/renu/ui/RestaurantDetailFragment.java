package com.moringaschool.renu.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.moringaschool.renu.CheckoutDialogFragment;
import com.moringaschool.renu.R;
import com.moringaschool.renu.adapters.RestaurantPagerAdapter;
import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Business mRestaurant;

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }

    public static RestaurantDetailFragment newInstance(Business restaurant) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurant = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get().load(mRestaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);
        List<String> categories = new ArrayList<>();

        for (Category category: mRestaurant.getCategories()) {
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mSaveRestaurantButton.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v){
        if ( v == mSaveRestaurantButton){
            FragmentManager fragmentManager = getChildFragmentManager();
            CheckoutDialogFragment checkoutDialogFragment = new CheckoutDialogFragment();
            checkoutDialogFragment.show(fragmentManager, "To checkout");
        }

    }
}