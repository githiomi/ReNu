package com.moringaschool.renu.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.renu.fragments.CheckoutDialogFragment;
import com.moringaschool.renu.R;
import com.moringaschool.renu.models.ApiConstants;
import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
//    Binding views using Butter knife
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

//    Shared Preferences variables
    private SharedPreferences mSharedPreferences;

//    Local variables
    private Business mRestaurant;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUsername;
    private String tableNumber;

//    Picasso resizing dimensions
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

//        Instantiating firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                mUsername = firebaseUser.getDisplayName();
            }
        };

//        Shared preferences getting data and storing
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        tableNumber = mSharedPreferences.getString("tableNumber", null);
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
    public void onClick(View v) {
        if (v == mSaveRestaurantButton) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference(ApiConstants.FIREBASE_CHILD_RESTAURANTS)
                    .child(mUsername)
                    .child("Table Number: " + tableNumber)
                    .child(mRestaurant.name);

            databaseReference.setValue(mRestaurant);
            Toast.makeText(getContext(), "Saved " + mRestaurant.getName() + " to database!", Toast.LENGTH_SHORT).show();
        }
    }

//    Overriding the cycle classes
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( mAuth != null ){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}