package com.moringaschool.renu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.renu.R;
import com.moringaschool.renu.adapters.FirebaseOrderViewHolder;
import com.moringaschool.renu.fragments.CheckoutDialogFragment;
import com.moringaschool.renu.models.ApiConstants;
import com.moringaschool.renu.models.Restaurant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener{

//    Binding views using Butter knife
    @BindView(R.id.rlCheckout) RelativeLayout mCheckout;
    @BindView(R.id.rlPay) RelativeLayout mPay;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.btnPay) Button mBtnPay;

//    Local variables
    private DatabaseReference mRestaurantReference;
    private FirebaseRecyclerAdapter<Restaurant, FirebaseOrderViewHolder> mFirebaseAdapter;
    private SharedPreferences sharedPreferences;
    private String mTableNumber;
    private String mUsername;

    @BindView(R.id.rvRestaurants) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

//        Binding views with Butter knife
        ButterKnife.bind(this);

//        Setting an on click listener for the pay button
        mBtnPay.setOnClickListener(this);

//        Making the checkout button invisible
        mCheckout.setVisibility(View.INVISIBLE);
        mPay.setVisibility(View.VISIBLE);

//        Getting data from shared preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mTableNumber = sharedPreferences.getString("tableNumber", null);
        mUsername = sharedPreferences.getString("username", null);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference(ApiConstants.FIREBASE_CHILD_RESTAURANTS)
                                                .child(mUsername)
                                                .child(mTableNumber);

//        Changing the app bar
        getSupportActionBar().setTitle("Confirm table " + mTableNumber + " order");

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

//    OnClick listeners
    @Override
    public void onClick( View v ){

        if ( v == mBtnPay ){

            FragmentManager fragmentManager = getSupportFragmentManager();
            CheckoutDialogFragment checkoutDialogFragment = new CheckoutDialogFragment();
            checkoutDialogFragment.show(fragmentManager, "To checkout");

        }
    }


//    Overriding the class methods
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
