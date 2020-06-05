package com.moringaschool.renu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.renu.R;
import com.moringaschool.renu.adapters.FirebaseOrderViewHolder;
import com.moringaschool.renu.models.ApiConstants;
import com.moringaschool.renu.models.Restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {

//    Binding views using Butter knife
    @BindView(R.id.rlCheckout) RelativeLayout mCheckout;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

//    Local variables
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter<Restaurant, FirebaseOrderViewHolder> mFirebaseAdapter;

    @BindView(R.id.rvRestaurants) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

//        Binding views with Butter knife
        ButterKnife.bind(this);

//        Making the checkout button invisible
        mCheckout.setVisibility(View.INVISIBLE);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference(ApiConstants.FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Restaurant> options =
                new FirebaseRecyclerOptions.Builder<Restaurant>()
                        .setQuery(mRestaurantReference, Restaurant.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseOrderViewHolder firebaseRestaurantViewHolder, int position, @NonNull Restaurant restaurant) {
                firebaseRestaurantViewHolder.bindRestaurant(restaurant);
            }

            @NonNull
            @Override
            public FirebaseOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
                mProgressBar.setVisibility(View.INVISIBLE);
                return new FirebaseOrderViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}
