package com.moringaschool.renu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.renu.R;
import com.moringaschool.renu.adapters.FirebaseOrderViewHolder;
import com.moringaschool.renu.adapters.RestaurantListAdapter;
import com.moringaschool.renu.fragments.CheckoutDialogFragment;
import com.moringaschool.renu.models.ApiConstants;
import com.moringaschool.renu.models.Business;
import com.moringaschool.renu.models.Restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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
    private RestaurantListAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private String mTableNumber;
    private String mUsername;
    private List<Business> mRestaurants;

    @BindView(R.id.rvRestaurants) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

//        Binding views with Butter knife
        ButterKnife.bind(this);

//        Getting data from shared preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mTableNumber = sharedPreferences.getString("tableNumber", null);
        mUsername = sharedPreferences.getString("username", null);

        mRestaurantReference = FirebaseDatabase.getInstance().getReference(ApiConstants.FIREBASE_CHILD_RESTAURANTS)
                .child(mUsername)
                .child(mTableNumber);

//        Custom method to display orders placed
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e){
            System.out.println(e);
        }

        retrieveOrders();

//        Setting an on click listener for the pay button
        mBtnPay.setOnClickListener(this);

//        Making the checkout button invisible
        mCheckout.setVisibility(View.INVISIBLE);
        mPay.setVisibility(View.VISIBLE);

//        Changing the app bar
        getSupportActionBar().setTitle("Confirm table " + mTableNumber + " order");
    }

    private void retrieveOrders() {

        mRestaurants = new ArrayList<>();

//        Variables to help access the table child
        DatabaseReference databaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference(ApiConstants.FIREBASE_CHILD_RESTAURANTS)
                        .child(mUsername)
                        .child("Table Number: " + mTableNumber);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mRestaurants.add(snapshot.getValue(Business.class));
                }

                mAdapter = new RestaurantListAdapter(CheckoutActivity.this, mRestaurants);
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(CheckoutActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);

                hideProgressBar();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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

//    Custom method to make the bar disappear
    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
